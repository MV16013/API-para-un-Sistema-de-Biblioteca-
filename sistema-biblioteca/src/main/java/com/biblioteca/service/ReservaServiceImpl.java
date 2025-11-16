package com.biblioteca.service;

import com.biblioteca.dto.reserva.ReservaCreateDTO;
import com.biblioteca.dto.reserva.ReservaResponseDTO;
import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Reserva;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoLibro;
import com.biblioteca.enums.EstadoReserva;
import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.ReservaRepository;
import com.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    @Override
    @Transactional
    public ReservaResponseDTO crearReserva(ReservaCreateDTO dto) {
        // 1. Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + dto.getUsuarioId()));

        // 2. Validar que el libro existe
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Libro no encontrado con id: " + dto.getLibroId()));

        // 3. Validar que el usuario puede reservar (está ACTIVO)
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new IllegalArgumentException(
                    "El usuario no está activo. Estado: " + usuario.getEstado());
        }

        // 4. Validar que el libro NO está disponible (solo se reserva si no hay stock)
        if (libro.getEstado() == EstadoLibro.DISPONIBLE && libro.getStockDisponible() > 0) {
            throw new IllegalArgumentException(
                    "El libro está disponible. Puede realizar un préstamo directamente.");
        }

        // 5. Validar que el usuario no tenga ya una reserva activa del mismo libro
        if (reservaRepository.existeReservaActivaUsuarioLibro(usuario.getId(), libro.getId())) {
            throw new IllegalArgumentException(
                    "El usuario ya tiene una reserva activa de este libro");
        }

        // 6. Validar cantidad de reservas activas (máximo 3)
        Long reservasActivas = reservaRepository.contarReservasActivasPorUsuario(usuario.getId());
        if (reservasActivas >= 3) {
            throw new IllegalArgumentException(
                    "El usuario ya tiene el máximo de reservas activas permitidas (3)");
        }

        // 7. Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setLibro(libro);
        reserva.setObservaciones(dto.getObservaciones());

        // 8. Actualizar estado del libro si es necesario
        if (libro.getEstado() == EstadoLibro.DISPONIBLE) {
            libro.setEstado(EstadoLibro.RESERVADO);
            libroRepository.save(libro);
        }

        Reserva reservaGuardada = reservaRepository.save(reserva);

        return convertirAResponseDTO(reservaGuardada);
    }

    @Override
    public ReservaResponseDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reserva no encontrada con id: " + id));
        return convertirAResponseDTO(reserva);
    }

    @Override
    public List<ReservaResponseDTO> listarTodasLasReservas() {
        return reservaRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarReservasPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return reservaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarReservasPorLibro(Long libroId) {
        if (!libroRepository.existsById(libroId)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + libroId);
        }

        return reservaRepository.findByLibroId(libroId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarReservasActivas() {
        return reservaRepository.findByEstado(EstadoReserva.ACTIVA).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarReservasVencidas() {
        return reservaRepository.findReservasVencidas(LocalDateTime.now()).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaResponseDTO cancelarReserva(Long id, String motivo) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reserva no encontrada con id: " + id));

        if (reserva.getEstado() != EstadoReserva.ACTIVA) {
            throw new IllegalArgumentException(
                    "La reserva no está activa. Estado: " + reserva.getEstado());
        }

        reserva.marcarComoCancelada();

        // Agregar motivo a las observaciones
        if (motivo != null && !motivo.isBlank()) {
            String obsActuales = reserva.getObservaciones() != null ?
                    reserva.getObservaciones() + " | " : "";
            reserva.setObservaciones(obsActuales + "Cancelación: " + motivo);
        }

        Reserva reservaActualizada = reservaRepository.save(reserva);

        return convertirAResponseDTO(reservaActualizada);
    }

    @Override
    @Transactional
    public void procesarReservasVencidas() {
        List<Reserva> reservasVencidas = reservaRepository.findReservasVencidas(LocalDateTime.now());

        for (Reserva reserva : reservasVencidas) {
            reserva.marcarComoVencida();
            reservaRepository.save(reserva);
        }
    }

    @Override
    @Transactional
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + id);
        }
        reservaRepository.deleteById(id);
    }

    private ReservaResponseDTO convertirAResponseDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setUsuarioId(reserva.getUsuario().getId());
        dto.setUsuarioNombre(reserva.getUsuario().getNombreCompleto());
        dto.setLibroId(reserva.getLibro().getId());
        dto.setLibroTitulo(reserva.getLibro().getTitulo());
        dto.setLibroIsbn(reserva.getLibro().getIsbn());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setFechaVencimiento(reserva.getFechaVencimiento());
        dto.setEstado(reserva.getEstado());
        dto.setObservaciones(reserva.getObservaciones());
        dto.setVigente(reserva.estaVigente());
        dto.setVencida(reserva.estaVencida());
        return dto;
    }
}