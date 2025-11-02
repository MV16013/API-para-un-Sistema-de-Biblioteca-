package com.biblioteca.service;

import com.biblioteca.dto.usuario.UsuarioCreateDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.usuario.UsuarioUpdateDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto);
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);
    List<UsuarioResponseDTO> listarTodosLosUsuarios();
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto);
    void eliminarUsuario(Long id);
    UsuarioResponseDTO suspenderUsuario(Long id);
    UsuarioResponseDTO activarUsuario(Long id);
}