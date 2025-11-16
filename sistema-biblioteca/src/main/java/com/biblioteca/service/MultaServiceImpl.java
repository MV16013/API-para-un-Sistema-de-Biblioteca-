package com.biblioteca.service;

import com.biblioteca.dto.multa.MultaCreateDTO;
import com.biblioteca.dto.multa.MultaResponseDTO;
import com.biblioteca.entity.Multa;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoMulta;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultaServiceImpl implements MultaService {

    private final MultaRepository multaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    @Override
    @Transactional
    public MultaResponseDTO crearMulta(MultaCreateDTO dto) {
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + dto.getUsuarioId()));

        // Si se proporciona prestamoId, validar que existe
        Prestamo prestamo = null;
        if (dto.getPrestamoId() != null) {
            prestamo = prestamoRepository.findById(dto.getPrestamoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Préstamo no encontrado con id: " + dto.getPrestamoId()));
        }

        // Crear la multa
        Multa multa = new Multa();
        multa.setUsuario(usuario);
        multa.setPrestamo(prestamo);
        multa.setMonto(dto.getMonto());
        multa.setConcepto(dto.getConcepto());

        Multa multaGuardada = multaRepository.save(multa);

        return convertirAResponseDTO(multaGuardada);
    }

    @Override
    public MultaResponseDTO obtenerMultaPorId(Long id) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Multa no encontrada con id: " + id));
        return convertirAResponseDTO(multa);
    }

    @Override
    public List<MultaResponseDTO> listarTodasLasMultas() {
        return multaRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MultaResponseDTO> listarMultasPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return multaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MultaResponseDTO> listarMultasPendientes() {
        return multaRepository.findByEstado(EstadoMulta.PENDIENTE).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MultaResponseDTO pagarMulta(Long id) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Multa no encontrada con id: " + id));

        if (multa.getEstado() != EstadoMulta.PENDIENTE) {
            throw new IllegalArgumentException(
                    "La multa no está pendiente. Estado: " + multa.getEstado());
        }

        multa.marcarComoPagada();

        Multa multaActualizada = multaRepository.save(multa);

        return convertirAResponseDTO(multaActualizada);
    }

    @Override
    @Transactional
    public MultaResponseDTO cancelarMulta(Long id, String motivo) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Multa no encontrada con id: " + id));

        if (multa.getEstado() == EstadoMulta.PAGADA) {
            throw new IllegalArgumentException(
                    "No se puede cancelar una multa que ya fue pagada");
        }

        multa.marcarComoCancelada();

        Multa multaActualizada = multaRepository.save(multa);

        return convertirAResponseDTO(multaActualizada);
    }

    @Override
    public BigDecimal calcularTotalMultasUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        BigDecimal total = multaRepository.calcularTotalMultasPendientes(usuarioId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional
    public void eliminarMulta(Long id) {
        if (!multaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Multa no encontrada con id: " + id);
        }
        multaRepository.deleteById(id);
    }

    private MultaResponseDTO convertirAResponseDTO(Multa multa) {
        MultaResponseDTO dto = new MultaResponseDTO();
        dto.setId(multa.getId());
        dto.setUsuarioId(multa.getUsuario().getId());
        dto.setUsuarioNombre(multa.getUsuario().getNombreCompleto());
        dto.setPrestamoId(multa.getPrestamo() != null ? multa.getPrestamo().getId() : null);
        dto.setMonto(multa.getMonto());
        dto.setConcepto(multa.getConcepto());
        dto.setFechaGeneracion(multa.getFechaGeneracion());
        dto.setFechaPago(multa.getFechaPago());
        dto.setEstado(multa.getEstado());
        dto.setPagada(multa.esPagada());
        dto.setPendiente(multa.estaPendiente());
        return dto;
    }
}
