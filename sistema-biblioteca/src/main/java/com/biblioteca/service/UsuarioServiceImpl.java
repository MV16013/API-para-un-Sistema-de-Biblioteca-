package com.biblioteca.service;

import com.biblioteca.dto.usuario.UsuarioCreateDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.usuario.UsuarioUpdateDTO;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;
    private final MultaRepository multaRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto) {
        // Validar que no exista el email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        // Validar que no exista el número de identificación
        if (usuarioRepository.existsByNumeroIdentificacion(dto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese número de identificación");
        }

        // Crear entidad
        Usuario usuario = new Usuario();
        usuario.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setEstado(EstadoUsuario.ACTIVO);

        // Guardar
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirAResponseDTO(usuarioGuardado);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return convertirAResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return convertirAResponseDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsuarioResponseDTO> listarUsuariosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::convertirAResponseDTO);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        // Actualizar solo los campos que vienen en el DTO
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            usuario.setApellido(dto.getApellido());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            // Validar que el nuevo email no esté en uso por otro usuario
            if (!usuario.getEmail().equals(dto.getEmail()) &&
                    usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getTelefono() != null) {
            usuario.setTelefono(dto.getTelefono());
        }
        if (dto.getDireccion() != null) {
            usuario.setDireccion(dto.getDireccion());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO suspenderUsuario(Long id, String motivo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setEstado(EstadoUsuario.SUSPENDIDO);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return convertirAResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setEstado(EstadoUsuario.ACTIVO);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return convertirAResponseDTO(usuarioActualizado);
    }

    @Override
    public List<UsuarioResponseDTO> buscarUsuariosPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuariosConPrestamosActivos() {
        return usuarioRepository.findUsuariosConPrestamosActivos().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuariosConMultasPendientes() {
        return usuarioRepository.findUsuariosConMultasPendientes().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existePorNumeroIdentificacion(String numeroIdentificacion) {
        return usuarioRepository.existsByNumeroIdentificacion(numeroIdentificacion);
    }

    @Override
    public boolean puedeRealizarPrestamo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));

        // Verificar estado activo
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            return false;
        }

        // Verificar multas pendientes
        Long multasPendientes = multaRepository.contarMultasPendientesPorUsuario(usuarioId);
        if (multasPendientes > 0) {
            return false;
        }

        // Verificar límite de préstamos activos
        Long prestamosActivos = prestamoRepository.contarPrestamosActivosPorUsuario(usuarioId);
        if (prestamosActivos >= 3) {
            return false;
        }

        return true;
    }

    // Método helper para conversión
    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNumeroIdentificacion(usuario.getNumeroIdentificacion());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccion(usuario.getDireccion());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setEstado(usuario.getEstado());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setFechaActualizacion(usuario.getFechaActualizacion());
        return dto;
    }
}