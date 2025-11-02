package com.biblioteca.service;

import com.biblioteca.dto.libro.LibroCreateDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.libro.LibroUpdateDTO;
import java.util.List;

public interface LibroService {
    LibroResponseDTO crearLibro(LibroCreateDTO dto);
    LibroResponseDTO obtenerLibroPorId(Long id);
    LibroResponseDTO obtenerLibroPorIsbn(String isbn);
    List<LibroResponseDTO> listarTodosLosLibros();
    List<LibroResponseDTO> buscarLibrosPorTitulo(String titulo);
    List<LibroResponseDTO> buscarLibrosPorAutor(String autor);
    List<LibroResponseDTO> listarLibrosDisponibles();
    LibroResponseDTO actualizarLibro(Long id, LibroUpdateDTO dto);
    void eliminarLibro(Long id);
}
