package com.biblioteca.service;

import com.biblioteca.dto.reserva.ReservaCreateDTO;
import com.biblioteca.dto.reserva.ReservaResponseDTO;
import java.util.List;

public interface ReservaService {

    // CRUD básico
    ReservaResponseDTO crearReserva(ReservaCreateDTO dto);
    ReservaResponseDTO obtenerReservaPorId(Long id);
    List<ReservaResponseDTO> listarTodasLasReservas();
    void eliminarReserva(Long id);

    // Operaciones específicas
    ReservaResponseDTO cancelarReserva(Long id, String motivo);
    void procesarReservasVencidas();

    // Búsquedas
    List<ReservaResponseDTO> listarReservasPorUsuario(Long usuarioId);
    List<ReservaResponseDTO> listarReservasPorLibro(Long libroId);
    List<ReservaResponseDTO> listarReservasActivas();
    List<ReservaResponseDTO> listarReservasVencidas();
}
