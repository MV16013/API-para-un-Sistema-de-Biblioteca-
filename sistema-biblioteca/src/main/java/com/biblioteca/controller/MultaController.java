package com.biblioteca.controller;

import com.biblioteca.dto.multa.MultaCreateDTO;
import com.biblioteca.dto.multa.MultaResponseDTO;
import com.biblioteca.service.MultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/multas")
@RequiredArgsConstructor
public class MultaController {

    private final MultaService multaService;

    @PostMapping
    public ResponseEntity<MultaResponseDTO> crearMulta(@Valid @RequestBody MultaCreateDTO dto) {
        MultaResponseDTO multa = multaService.crearMulta(dto);
        return new ResponseEntity<>(multa, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MultaResponseDTO> obtenerMulta(@PathVariable Long id) {
        MultaResponseDTO multa = multaService.obtenerMultaPorId(id);
        return ResponseEntity.ok(multa);
    }

    @GetMapping
    public ResponseEntity<List<MultaResponseDTO>> listarMultas() {
        List<MultaResponseDTO> multas = multaService.listarTodasLasMultas();
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MultaResponseDTO>> listarMultasPorUsuario(@PathVariable Long usuarioId) {
        List<MultaResponseDTO> multas = multaService.listarMultasPorUsuario(usuarioId);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<MultaResponseDTO>> listarMultasPendientes() {
        List<MultaResponseDTO> multas = multaService.listarMultasPendientes();
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/usuario/{usuarioId}/total")
    public ResponseEntity<BigDecimal> calcularTotalMultasUsuario(@PathVariable Long usuarioId) {
        BigDecimal total = multaService.calcularTotalMultasUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<MultaResponseDTO> pagarMulta(@PathVariable Long id) {
        MultaResponseDTO multa = multaService.pagarMulta(id);
        return ResponseEntity.ok(multa);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<MultaResponseDTO> cancelarMulta(
            @PathVariable Long id,
            @RequestParam String motivo) {
        MultaResponseDTO multa = multaService.cancelarMulta(id, motivo);
        return ResponseEntity.ok(multa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long id) {
        multaService.eliminarMulta(id);
        return ResponseEntity.noContent().build();
    }
}
