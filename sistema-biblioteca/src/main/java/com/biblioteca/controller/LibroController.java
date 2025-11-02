package com.biblioteca.controller;

import com.biblioteca.dto.libro.LibroCreateDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.libro.LibroUpdateDTO;
import com.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<LibroResponseDTO> crearLibro(@Valid @RequestBody LibroCreateDTO dto) {
        LibroResponseDTO libro = libroService.crearLibro(dto);
        return new ResponseEntity<>(libro, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerLibro(@PathVariable Long id) {
        LibroResponseDTO libro = libroService.obtenerLibroPorId(id);
        return ResponseEntity.ok(libro);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LibroResponseDTO> obtenerLibroPorIsbn(@PathVariable String isbn) {
        LibroResponseDTO libro = libroService.obtenerLibroPorIsbn(isbn);
        return ResponseEntity.ok(libro);
    }

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> listarLibros() {
        List<LibroResponseDTO> libros = libroService.listarTodosLosLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<LibroResponseDTO> libros = libroService.buscarLibrosPorTitulo(titulo);
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorAutor(@RequestParam String autor) {
        List<LibroResponseDTO> libros = libroService.buscarLibrosPorAutor(autor);
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<LibroResponseDTO>> listarLibrosDisponibles() {
        List<LibroResponseDTO> libros = libroService.listarLibrosDisponibles();
        return ResponseEntity.ok(libros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroUpdateDTO dto) {
        LibroResponseDTO libro = libroService.actualizarLibro(id, dto);
        return ResponseEntity.ok(libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}