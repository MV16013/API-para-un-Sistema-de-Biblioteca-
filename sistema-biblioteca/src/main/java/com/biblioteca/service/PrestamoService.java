package com.biblioteca.service;

import com.biblioteca.dto.prestamo.PrestamoCreateDTO;
import com.biblioteca.dto.prestamo.PrestamoResponseDTO;
import com.biblioteca.dto.prestamo.PrestamoSimpleResponseDTO;
import java.util.List;

public interface PrestamoService {
    PrestamoSimpleResponseDTO crearPrestamo(PrestamoCreateDTO dto);
    PrestamoResponseDTO obtenerPrestamoPorId(Long id);
    PrestamoSimpleResponseDTO obtenerPrestamoSimplePorId(Long id);
    List<PrestamoSimpleResponseDTO> listarTodosLosPrestamos();
    List<PrestamoSimpleResponseDTO> listarPrestamosPorUsuario(Long usuarioId);
    List<PrestamoSimpleResponseDTO> listarPrestamosActivos();
    List<PrestamoSimpleResponseDTO> listarPrestamosVencidos();
    PrestamoSimpleResponseDTO devolverLibro(Long prestamoId, String observaciones);
    void eliminarPrestamo(Long id);
}