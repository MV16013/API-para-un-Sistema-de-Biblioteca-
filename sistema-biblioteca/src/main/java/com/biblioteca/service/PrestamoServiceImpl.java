package com.biblioteca.service;

import com.biblioteca.dto.prestamo.PrestamoCreateDTO;
import com.biblioteca.dto.prestamo.PrestamoResponseDTO;
import com.biblioteca.dto.prestamo.PrestamoSimpleResponseDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoPrestamo;
import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
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

        // 5. Validar cantidad de préstamos activos (máximo 3)
        Long prestamosActivos = prestamoRepository.contarPrestamosActivosPorUsuario(usuario.getId());
        if (prestamosActivos >= 3) {
            throw new IllegalArgumentException(
                    "El usuario ya tiene el máximo de préstamos activos permitidos (3)");
        }

        // 6. Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaDevolucionPrevista(
                LocalDateTime.now().plusDays(dto.getDiasPrestamo()));
        prestamo.setObservaciones(dto.getObservaciones());

        // 7. Reducir stock del libro
        libro.reducirStock();
        libroRepository.save(libro);

        // 8. Guardar préstamo
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
    public List<PrestamoSimpleResponseDTO> listarPrestamosPorUsuario(Long usuarioId) {
        // Validar que el usuario existe
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return prestamoRepository.findByUsuarioId(usuarioId).stream()
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

        // Marcar como devuelto
        prestamo.marcarComoDevuelto();

        // Aumentar stock del libro
        Libro libro = prestamo.getLibro();
        libro.aumentarStock();
        libroRepository.save(libro);

        // Agregar observaciones de devolución
        if (observaciones != null && !observaciones.isBlank()) {
            String obsActuales = prestamo.getObservaciones() != null ?
                    prestamo.getObservaciones() + " | " : "";
            prestamo.setObservaciones(obsActuales + "Devolución: " + observaciones);
        }

        // Actualizar estado si está vencido
        if (prestamo.getMulta().compareTo(java.math.BigDecimal.ZERO) > 0) {
            prestamo.setEstado(EstadoPrestamo.VENCIDO);
        }

        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        return convertirASimpleResponseDTO(prestamoActualizado);
    }

    @Override
    @Transactional
    public void eliminarPrestamo(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Préstamo no encontrado con id: " + id);
        }
        prestamoRepository.deleteById(id);
    }

    // ==================== MÉTODOS HELPER DE CONVERSIÓN ====================

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

        // Datos del usuario
        dto.setUsuarioId(prestamo.getUsuario().getId());
        dto.setUsuarioNombre(prestamo.getUsuario().getNombreCompleto());

        // Datos del libro
        dto.setLibroId(prestamo.getLibro().getId());
        dto.setLibroTitulo(prestamo.getLibro().getTitulo());
        dto.setLibroAutor(prestamo.getLibro().getAutor());
        dto.setLibroIsbn(prestamo.getLibro().getIsbn());

        // Datos del préstamo
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