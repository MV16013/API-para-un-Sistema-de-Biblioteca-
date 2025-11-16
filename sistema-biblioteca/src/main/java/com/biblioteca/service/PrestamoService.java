package com.biblioteca.service;

import com.biblioteca.dto.prestamo.PrestamoCreateDTO;
import com.biblioteca.dto.prestamo.PrestamoResponseDTO;
import com.biblioteca.dto.prestamo.PrestamoSimpleResponseDTO;
import java.util.List;

public interface PrestamoService {

    // CRUD básico
    PrestamoSimpleResponseDTO crearPrestamo(PrestamoCreateDTO dto);
    PrestamoResponseDTO obtenerPrestamoPorId(Long id);
    PrestamoSimpleResponseDTO obtenerPrestamoSimplePorId(Long id);
    List<PrestamoSimpleResponseDTO> listarTodosLosPrestamos();
    void eliminarPrestamo(Long id);

    // Operaciones específicas
    PrestamoSimpleResponseDTO devolverLibro(Long prestamoId, String observaciones);
    PrestamoSimpleResponseDTO renovarPrestamo(Long prestamoId);

    // Búsquedas
    List<PrestamoSimpleResponseDTO> listarPrestamosPorUsuario(Long usuarioId);
    List<PrestamoSimpleResponseDTO> listarPrestamosPorLibro(Long libroId);
    List<PrestamoSimpleResponseDTO> listarPrestamosActivos();
    List<PrestamoSimpleResponseDTO> listarPrestamosVencidos();
    List<PrestamoSimpleResponseDTO> listarPrestamosProximosAVencer(Integer dias);
}