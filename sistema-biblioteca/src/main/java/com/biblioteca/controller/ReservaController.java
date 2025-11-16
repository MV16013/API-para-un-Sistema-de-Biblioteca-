package com.biblioteca.controller;

import com.biblioteca.dto.reserva.ReservaCreateDTO;
import com.biblioteca.dto.reserva.ReservaResponseDTO;
import com.biblioteca.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaCreateDTO dto) {
        ReservaResponseDTO reserva = reservaService.crearReserva(dto);
        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable Long id) {
        ReservaResponseDTO reserva = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        List<ReservaResponseDTO> reservas = reservaService.listarTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorUsuario(@PathVariable Long usuarioId) {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorLibro(@PathVariable Long libroId) {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasPorLibro(libroId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasActivas() {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasActivas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/vencidas")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasVencidas() {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasVencidas();
        return ResponseEntity.ok(reservas);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(
            @PathVariable Long id,
            @RequestParam(required = false) String motivo) {
        ReservaResponseDTO reserva = reservaService.cancelarReserva(id, motivo);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping("/procesar-vencidas")
    public ResponseEntity<Void> procesarReservasVencidas() {
        reservaService.procesarReservasVencidas();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}