package com.biblioteca.controller;

import com.biblioteca.dto.usuario.UsuarioCreateDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.dto.usuario.UsuarioUpdateDTO;
import com.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/paginados")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuariosPaginados(Pageable pageable) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuariosPaginados(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/con-prestamos-activos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosConPrestamosActivos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuariosConPrestamosActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/con-multas-pendientes")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosConMultasPendientes() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuariosConMultasPendientes();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}/puede-prestar")
    public ResponseEntity<Boolean> puedeRealizarPrestamo(@PathVariable Long id) {
        boolean puede = usuarioService.puedeRealizarPrestamo(id);
        return ResponseEntity.ok(puede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}/suspender")
    public ResponseEntity<UsuarioResponseDTO> suspenderUsuario(
            @PathVariable Long id,
            @RequestParam String motivo) {
        UsuarioResponseDTO usuario = usuarioService.suspenderUsuario(id, motivo);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<UsuarioResponseDTO> activarUsuario(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.activarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}