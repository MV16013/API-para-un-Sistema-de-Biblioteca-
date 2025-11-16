package com.biblioteca.service;

import com.biblioteca.dto.libro.LibroCreateDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.libro.LibroUpdateDTO;
import java.util.List;

public interface LibroService {

    // CRUD básico
    LibroResponseDTO crearLibro(LibroCreateDTO dto);
    LibroResponseDTO obtenerLibroPorId(Long id);
    LibroResponseDTO obtenerLibroPorIsbn(String isbn);
    List<LibroResponseDTO> listarTodosLosLibros();
    LibroResponseDTO actualizarLibro(Long id, LibroUpdateDTO dto);
    void eliminarLibro(Long id);

    // Búsquedas específicas
    List<LibroResponseDTO> buscarLibrosPorTitulo(String titulo);
    List<LibroResponseDTO> buscarLibrosPorAutor(Long autorId);
    List<LibroResponseDTO> buscarLibrosPorAutorNombre(String nombre);
    List<LibroResponseDTO> buscarLibrosPorCategoria(Long categoriaId);
    List<LibroResponseDTO> buscarLibrosPorCategoriaCodigo(String codigo);
    List<LibroResponseDTO> listarLibrosDisponibles();
    List<LibroResponseDTO> busquedaAvanzada(String busqueda);
}