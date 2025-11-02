package com.biblioteca.service;

import com.biblioteca.dto.libro.LibroCreateDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.libro.LibroUpdateDTO;
import com.biblioteca.entity.Libro;
import com.biblioteca.enums.EstadoLibro;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    @Override
    @Transactional
    public LibroResponseDTO crearLibro(LibroCreateDTO dto) {
        // Validar que no exista el ISBN
        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN");
        }

        // Crear entidad
        Libro libro = new Libro();
        libro.setIsbn(dto.getIsbn());
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setEditorial(dto.getEditorial());
        libro.setDescripcion(dto.getDescripcion());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setCategoria(dto.getCategoria());
        libro.setStockTotal(dto.getStockTotal());
        libro.setStockDisponible(dto.getStockTotal());
        libro.setEstado(EstadoLibro.DISPONIBLE);

        // Guardar
        Libro libroGuardado = libroRepository.save(libro);

        return convertirAResponseDTO(libroGuardado);
    }

    @Override
    public LibroResponseDTO obtenerLibroPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
        return convertirAResponseDTO(libro);
    }

    @Override
    public LibroResponseDTO obtenerLibroPorIsbn(String isbn) {
        Libro libro = libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));
        return convertirAResponseDTO(libro);
    }

    @Override
    public List<LibroResponseDTO> listarTodosLosLibros() {
        return libroRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> listarLibrosDisponibles() {
        return libroRepository.findByStockDisponibleGreaterThan(0).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LibroResponseDTO actualizarLibro(Long id, LibroUpdateDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        // Actualizar solo los campos que vienen en el DTO
        if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
            libro.setTitulo(dto.getTitulo());
        }
        if (dto.getAutor() != null) {
            libro.setAutor(dto.getAutor());
        }
        if (dto.getEditorial() != null) {
            libro.setEditorial(dto.getEditorial());
        }
        if (dto.getDescripcion() != null) {
            libro.setDescripcion(dto.getDescripcion());
        }
        if (dto.getAnioPublicacion() != null) {
            libro.setAnioPublicacion(dto.getAnioPublicacion());
        }
        if (dto.getCategoria() != null) {
            libro.setCategoria(dto.getCategoria());
        }
        if (dto.getStockTotal() != null) {
            libro.setStockTotal(dto.getStockTotal());
            // Ajustar stock disponible si es necesario
            if (libro.getStockDisponible() > dto.getStockTotal()) {
                libro.setStockDisponible(dto.getStockTotal());
            }
        }

        Libro libroActualizado = libroRepository.save(libro);
        return convertirAResponseDTO(libroActualizado);
    }

    @Override
    @Transactional
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + id);
        }
        libroRepository.deleteById(id);
    }

    // Método helper para conversión
    private LibroResponseDTO convertirAResponseDTO(Libro libro) {
        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setId(libro.getId());
        dto.setIsbn(libro.getIsbn());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setEditorial(libro.getEditorial());
        dto.setDescripcion(libro.getDescripcion());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setCategoria(libro.getCategoria());
        dto.setEstado(libro.getEstado());
        dto.setStockTotal(libro.getStockTotal());
        dto.setStockDisponible(libro.getStockDisponible());
        dto.setFechaRegistro(libro.getFechaRegistro());
        dto.setFechaActualizacion(libro.getFechaActualizacion());
        dto.setDisponible(libro.estaDisponible());
        return dto;
    }
}