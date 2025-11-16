package com.biblioteca.service;

import com.biblioteca.dto.multa.MultaCreateDTO;
import com.biblioteca.dto.multa.MultaResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public interface MultaService {

    // CRUD básico
    MultaResponseDTO crearMulta(MultaCreateDTO dto);

    MultaResponseDTO obtenerMultaPorId(Long id);

    List<MultaResponseDTO> listarTodasLasMultas();

    void eliminarMulta(Long id);

    // Operaciones específicas
    MultaResponseDTO pagarMulta(Long id);

    MultaResponseDTO cancelarMulta(Long id, String motivo);

    List<MultaResponseDTO> listarMultasPorUsuario(Long usuarioId);

    List<MultaResponseDTO> listarMultasPendientes();

    BigDecimal calcularTotalMultasUsuario(Long usuarioId);
}
