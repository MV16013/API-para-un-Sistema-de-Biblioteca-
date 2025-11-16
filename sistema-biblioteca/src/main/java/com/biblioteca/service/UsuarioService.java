package com.biblioteca.service;

import com.biblioteca.dto.usuario.UsuarioCreateDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.usuario.UsuarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto);
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);
    UsuarioResponseDTO obtenerUsuarioPorEmail(String email);
    List<UsuarioResponseDTO> listarTodosLosUsuarios();
    Page<UsuarioResponseDTO> listarUsuariosPaginados(Pageable pageable);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto);
    void eliminarUsuario(Long id);


    UsuarioResponseDTO suspenderUsuario(Long id, String motivo);
    UsuarioResponseDTO activarUsuario(Long id);
    List<UsuarioResponseDTO> buscarUsuariosPorNombre(String nombre);
    List<UsuarioResponseDTO> listarUsuariosConPrestamosActivos();
    List<UsuarioResponseDTO> listarUsuariosConMultasPendientes();

    boolean existePorEmail(String email);
    boolean existePorNumeroIdentificacion(String numeroIdentificacion);
    boolean puedeRealizarPrestamo(Long usuarioId);
}