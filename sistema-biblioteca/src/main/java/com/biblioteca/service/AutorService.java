package com.biblioteca.service;

import com.biblioteca.dto.autor.AutorCreateDTO;
import com.biblioteca.dto.autor.AutorResponseDTO;
import com.biblioteca.dto.autor.AutorUpdateDTO;
import java.util.List;

public interface AutorService {

    // CRUD básico
    AutorResponseDTO crearAutor(AutorCreateDTO dto);
    AutorResponseDTO obtenerAutorPorId(Long id);
    List<AutorResponseDTO> listarTodosLosAutores();
    AutorResponseDTO actualizarAutor(Long id, AutorUpdateDTO dto);
    void eliminarAutor(Long id);

    // Búsquedas específicas
    List<AutorResponseDTO> buscarAutoresPorNombre(String nombre);
    List<AutorResponseDTO> buscarAutoresPorNacionalidad(String nacionalidad);
    List<AutorResponseDTO> listarAutoresConLibrosDisponibles();
}