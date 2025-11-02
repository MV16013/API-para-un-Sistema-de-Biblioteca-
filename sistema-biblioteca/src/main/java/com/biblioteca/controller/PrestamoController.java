package com.biblioteca.controller;

import com.biblioteca.dto.prestamo.PrestamoCreateDTO;
import com.biblioteca.dto.prestamo.PrestamoResponseDTO;
import com.biblioteca.dto.prestamo.PrestamoSimpleResponseDTO;
import com.biblioteca.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoSimpleResponseDTO> crearPrestamo(
            @Valid @RequestBody PrestamoCreateDTO dto) {
        PrestamoSimpleResponseDTO prestamo = prestamoService.crearPrestamo(dto);
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerPrestamo(@PathVariable Long id) {
        PrestamoResponseDTO prestamo = prestamoService.obtenerPrestamoPorId(id);
        return ResponseEntity.ok(prestamo);
    }

    @GetMapping("/{id}/simple")
    public ResponseEntity<PrestamoSimpleResponseDTO> obtenerPrestamoSimple(@PathVariable Long id) {
        PrestamoSimpleResponseDTO prestamo = prestamoService.obtenerPrestamoSimplePorId(id);
        return ResponseEntity.ok(prestamo);
    }

    @GetMapping
    public ResponseEntity<List<PrestamoSimpleResponseDTO>> listarPrestamos() {
        List<PrestamoSimpleResponseDTO> prestamos = prestamoService.listarTodosLosPrestamos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoSimpleResponseDTO>> listarPrestamosPorUsuario(
            @PathVariable Long usuarioId) {
        List<PrestamoSimpleResponseDTO> prestamos =
                prestamoService.listarPrestamosPorUsuario(usuarioId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoSimpleResponseDTO>> listarPrestamosActivos() {
        List<PrestamoSimpleResponseDTO> prestamos = prestamoService.listarPrestamosActivos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoSimpleResponseDTO>> listarPrestamosVencidos() {
        List<PrestamoSimpleResponseDTO> prestamos = prestamoService.listarPrestamosVencidos();
        return ResponseEntity.ok(prestamos);
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoSimpleResponseDTO> devolverLibro(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String observaciones = body != null ? body.get("observaciones") : null;
        PrestamoSimpleResponseDTO prestamo = prestamoService.devolverLibro(id, observaciones);
        return ResponseEntity.ok(prestamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }
}