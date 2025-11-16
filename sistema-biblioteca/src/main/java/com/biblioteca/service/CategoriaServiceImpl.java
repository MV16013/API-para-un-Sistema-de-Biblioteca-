package com.biblioteca.service;

import com.biblioteca.dto.categoria.CategoriaCreateDTO;
import com.biblioteca.dto.categoria.CategoriaResponseDTO;
import com.biblioteca.dto.categoria.CategoriaUpdateDTO;
import com.biblioteca.entity.Categoria;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaCreateDTO dto) {
        // Validar que no exista el código
        if (categoriaRepository.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese código");
        }

        Categoria categoria = new Categoria();
        categoria.setCodigo(dto.getCodigo().toUpperCase());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirAResponseDTO(categoriaGuardada);
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return convertirAResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriaPorCodigo(String codigo) {
        Categoria categoria = categoriaRepository.findByCodigo(codigo.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con código: " + codigo));
        return convertirAResponseDTO(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> listarTodasLasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaUpdateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            categoria.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            categoria.setDescripcion(dto.getDescripcion());
        }

        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return convertirAResponseDTO(categoriaActualizada);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<CategoriaResponseDTO> buscarCategoriasPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaResponseDTO> listarCategoriasConLibrosDisponibles() {
        return categoriaRepository.findCategoriasConLibrosDisponibles().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    private CategoriaResponseDTO convertirAResponseDTO(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setCodigo(categoria.getCodigo());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setFechaRegistro(categoria.getFechaRegistro());
        dto.setFechaActualizacion(categoria.getFechaActualizacion());
        dto.setValida(categoria.esValida());
        return dto;
    }
}