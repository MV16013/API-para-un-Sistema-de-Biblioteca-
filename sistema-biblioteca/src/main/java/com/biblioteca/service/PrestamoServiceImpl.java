package com.biblioteca.service;

import com.biblioteca.dto.prestamo.PrestamoCreateDTO;
import com.biblioteca.dto.prestamo.PrestamoResponseDTO;
import com.biblioteca.dto.prestamo.PrestamoSimpleResponseDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Multa;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoPrestamo;
import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final MultaRepository multaRepository;
    private final UsuarioService usuarioService;
    private final LibroService libroService;

    @Override
    @Transactional
    public PrestamoSimpleResponseDTO crearPrestamo(PrestamoCreateDTO dto) {
        // 1. Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + dto.getUsuarioId()));

        // 2. Validar que el libro existe
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Libro no encontrado con id: " + dto.getLibroId()));

        // 3. Validar que el usuario puede prestar (está ACTIVO)
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new IllegalArgumentException(
                    "El usuario no está activo. Estado: " + usuario.getEstado());
        }

        // 4. Validar que el libro está disponible
        if (!libro.estaDisponible()) {
            throw new IllegalArgumentException(
                    "El libro no está disponible. Estado: " + libro.getEstado() +
                            ", Stock disponible: " + libro.getStockDisponible());
        }

        // 5. Validar que no tenga multas pendientes
        Long multasPendientes = multaRepository.contarMultasPendientesPorUsuario(usuario.getId());
        if (multasPendientes > 0) {
            BigDecimal totalMultas = multaRepository.calcularTotalMultasPendientes(usuario.getId());
            throw new IllegalArgumentException(
                    "El usuario tiene " + multasPendientes + " multa(s) pendiente(s) por un total de $" +
                            totalMultas + ". Debe pagarlas antes de realizar un préstamo.");
        }

        // 6. Validar cantidad de préstamos activos (máximo 3)
        Long prestamosActivos = prestamoRepository.contarPrestamosActivosPorUsuario(usuario.getId());
        if (prestamosActivos >= 3) {
            throw new IllegalArgumentException(
                    "El usuario ya tiene el máximo de préstamos activos permitidos (3)");
        }

        // 7. Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaDevolucionPrevista(
                LocalDateTime.now().plusDays(dto.getDiasPrestamo()));
        prestamo.setObservaciones(dto.getObservaciones());

        // 8. Reducir stock del libro
        libro.reducirStock();
        libroRepository.save(libro);

        // 9. Guardar préstamo
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return convertirASimpleResponseDTO(prestamoGuardado);
    }

    @Override
    public PrestamoResponseDTO obtenerPrestamoPorId(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Préstamo no encontrado con id: " + id));
        return convertirAResponseDTO(prestamo);
    }

    @Override
    public PrestamoSimpleResponseDTO obtenerPrestamoSimplePorId(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Préstamo no encontrado con id: " + id));
        return convertirASimpleResponseDTO(prestamo);
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarTodosLosPrestamos() {
        return prestamoRepository.findAll().stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PrestamoSimpleResponseDTO devolverLibro(Long prestamoId, String observaciones) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Préstamo no encontrado con id: " + prestamoId));

        // Validar que el préstamo está activo
        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new IllegalArgumentException(
                    "El préstamo no está activo. Estado: " + prestamo.getEstado());
        }

        // Marcar como devuelto y calcular multa
        prestamo.marcarComoDevuelto();

        // Aumentar stock del libro
        Libro libro = prestamo.getLibro();
        libro.aumentarStock();
        libroRepository.save(libro);

        // Generar multa si hay retraso
        if (prestamo.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            Multa multa = new Multa();
            multa.setUsuario(prestamo.getUsuario());
            multa.setPrestamo(prestamo);
            multa.setMonto(prestamo.getMulta());
            multa.setConcepto("Multa por retraso de " + prestamo.calcularDiasRetraso() +
                    " días en devolución de: " + libro.getTitulo());
            multaRepository.save(multa);
        }

        // Agregar observaciones de devolución
        if (observaciones != null && !observaciones.isBlank()) {
            String obsActuales = prestamo.getObservaciones() != null ?
                    prestamo.getObservaciones() + " | " : "";
            prestamo.setObservaciones(obsActuales + "Devolución: " + observaciones);
        }

        // Actualizar estado si está vencido
        if (prestamo.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            prestamo.setEstado(EstadoPrestamo.VENCIDO);
        }

        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        return convertirASimpleResponseDTO(prestamoActualizado);
    }

    @Override
    @Transactional
    public PrestamoSimpleResponseDTO renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Préstamo no encontrado con id: " + prestamoId));

        // Validar que el préstamo está activo
        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new IllegalArgumentException(
                    "Solo se pueden renovar préstamos activos. Estado actual: " + prestamo.getEstado());
        }

        // Validar que no está vencido
        if (prestamo.estaVencido()) {
            throw new IllegalArgumentException(
                    "No se puede renovar un préstamo vencido. Días de retraso: " + prestamo.calcularDiasRetraso());
        }

        // Extender fecha de devolución por 7 días más
        prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista().plusDays(7));
        prestamo.setEstado(EstadoPrestamo.RENOVADO);

        String obsActuales = prestamo.getObservaciones() != null ?
                prestamo.getObservaciones() + " | " : "";
        prestamo.setObservaciones(obsActuales + "Renovado el " + LocalDateTime.now());

        Prestamo prestamoRenovado = prestamoRepository.save(prestamo);

        return convertirASimpleResponseDTO(prestamoRenovado);
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarPrestamosPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return prestamoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarPrestamosPorLibro(Long libroId) {
        if (!libroRepository.existsById(libroId)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + libroId);
        }

        return prestamoRepository.findByLibroId(libroId).stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarPrestamosActivos() {
        return prestamoRepository.findByEstado(EstadoPrestamo.ACTIVO).stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarPrestamosVencidos() {
        return prestamoRepository.findPrestamosVencidos(LocalDateTime.now()).stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoSimpleResponseDTO> listarPrestamosProximosAVencer(Integer dias) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(dias);

        return prestamoRepository.findPrestamosProximosAVencer(ahora, limite).stream()
                .map(this::convertirASimpleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarPrestamo(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Préstamo no encontrado con id: " + id);
        }
        prestamoRepository.deleteById(id);
    }

    // Métodos helper para conversión

    private PrestamoResponseDTO convertirAResponseDTO(Prestamo prestamo) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setId(prestamo.getId());

        // Convertir usuario a DTO
        UsuarioResponseDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(
                prestamo.getUsuario().getId());
        dto.setUsuario(usuarioDTO);

        // Convertir libro a DTO
        LibroResponseDTO libroDTO = libroService.obtenerLibroPorId(
                prestamo.getLibro().getId());
        dto.setLibro(libroDTO);

        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        dto.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        dto.setEstado(prestamo.getEstado());
        dto.setObservaciones(prestamo.getObservaciones());
        dto.setMulta(prestamo.getMulta());
        dto.setDiasRetraso(prestamo.calcularDiasRetraso());
        dto.setVencido(prestamo.estaVencido());

        return dto;
    }

    private PrestamoSimpleResponseDTO convertirASimpleResponseDTO(Prestamo prestamo) {
        PrestamoSimpleResponseDTO dto = new PrestamoSimpleResponseDTO();
        dto.setId(prestamo.getId());
        dto.setUsuarioId(prestamo.getUsuario().getId());
        dto.setUsuarioNombre(prestamo.getUsuario().getNombreCompleto());
        dto.setLibroId(prestamo.getLibro().getId());
        dto.setLibroTitulo(prestamo.getLibro().getTitulo());
        dto.setLibroIsbn(prestamo.getLibro().getIsbn());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        dto.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        dto.setEstado(prestamo.getEstado());
        dto.setObservaciones(prestamo.getObservaciones());
        dto.setMulta(prestamo.getMulta());
        dto.setDiasRetraso(prestamo.calcularDiasRetraso());
        dto.setVencido(prestamo.estaVencido());

        return dto;
    }
}