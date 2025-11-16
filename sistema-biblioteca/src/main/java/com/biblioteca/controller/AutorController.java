package com.biblioteca.controller;

import com.biblioteca.dto.autor.AutorCreateDTO;
import com.biblioteca.dto.autor.AutorResponseDTO;
import com.biblioteca.dto.autor.AutorUpdateDTO;
import com.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> crearAutor(@Valid @RequestBody AutorCreateDTO dto) {
        AutorResponseDTO autor = autorService.crearAutor(dto);
        return new ResponseEntity<>(autor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> obtenerAutor(@PathVariable Long id) {
        AutorResponseDTO autor = autorService.obtenerAutorPorId(id);
        return ResponseEntity.ok(autor);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarAutores() {
        List<AutorResponseDTO> autores = autorService.listarTodosLosAutores();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<AutorResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<AutorResponseDTO> autores = autorService.buscarAutoresPorNombre(nombre);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar/nacionalidad")
    public ResponseEntity<List<AutorResponseDTO>> buscarPorNacionalidad(@RequestParam String nacionalidad) {
        List<AutorResponseDTO> autores = autorService.buscarAutoresPorNacionalidad(nacionalidad);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/con-libros-disponibles")
    public ResponseEntity<List<AutorResponseDTO>> listarAutoresConLibrosDisponibles() {
        List<AutorResponseDTO> autores = autorService.listarAutoresConLibrosDisponibles();
        return ResponseEntity.ok(autores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> actualizarAutor(
            @PathVariable Long id,
            @Valid @RequestBody AutorUpdateDTO dto) {
        AutorResponseDTO autor = autorService.actualizarAutor(id, dto);
        return ResponseEntity.ok(autor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
        autorService.eliminarAutor(id);
        return ResponseEntity.noContent().build();
    }
}