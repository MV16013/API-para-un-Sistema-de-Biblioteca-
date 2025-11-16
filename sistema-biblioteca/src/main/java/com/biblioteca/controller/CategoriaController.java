package com.biblioteca.controller;

import com.biblioteca.dto.categoria.CategoriaCreateDTO;
import com.biblioteca.dto.categoria.CategoriaResponseDTO;
import com.biblioteca.dto.categoria.CategoriaUpdateDTO;
import com.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CategoriaCreateDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.crearCategoria(dto);
        return new ResponseEntity<>(categoria, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorCodigo(@PathVariable String codigo) {
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorCodigo(codigo);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> categorias = categoriaService.listarTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<CategoriaResponseDTO> categorias = categoriaService.buscarCategoriasPorNombre(nombre);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/con-libros-disponibles")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategoriasConLibrosDisponibles() {
        List<CategoriaResponseDTO> categorias = categoriaService.listarCategoriasConLibrosDisponibles();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaUpdateDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.actualizarCategoria(id, dto);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}