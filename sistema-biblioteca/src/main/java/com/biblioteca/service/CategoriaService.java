package com.biblioteca.service;

import com.biblioteca.dto.categoria.CategoriaCreateDTO;
import com.biblioteca.dto.categoria.CategoriaResponseDTO;
import com.biblioteca.dto.categoria.CategoriaUpdateDTO;
import java.util.List;

public interface CategoriaService {

    // CRUD básico
    CategoriaResponseDTO crearCategoria(CategoriaCreateDTO dto);
    CategoriaResponseDTO obtenerCategoriaPorId(Long id);
    CategoriaResponseDTO obtenerCategoriaPorCodigo(String codigo);
    List<CategoriaResponseDTO> listarTodasLasCategorias();
    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaUpdateDTO dto);
    void eliminarCategoria(Long id);

    // Búsquedas específicas
    List<CategoriaResponseDTO> buscarCategoriasPorNombre(String nombre);
    List<CategoriaResponseDTO> listarCategoriasConLibrosDisponibles();
}