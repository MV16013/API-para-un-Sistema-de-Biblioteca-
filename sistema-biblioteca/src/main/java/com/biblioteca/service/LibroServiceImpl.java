package com.biblioteca.service;

import com.biblioteca.dto.autor.AutorSimpleDTO;
import com.biblioteca.dto.categoria.CategoriaSimpleDTO;
import com.biblioteca.dto.libro.LibroCreateDTO;
import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.libro.LibroUpdateDTO;
import com.biblioteca.entity.Autor;
import com.biblioteca.entity.Categoria;
import com.biblioteca.entity.Libro;
import com.biblioteca.enums.EstadoLibro;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public LibroResponseDTO crearLibro(LibroCreateDTO dto) {
        // Validar que no exista el ISBN
        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN");
        }

        // Buscar autores
        List<Autor> autores = new ArrayList<>();
        for (Long autorId : dto.getAutoresIds()) {
            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Autor no encontrado con id: " + autorId));
            autores.add(autor);
        }

        // Buscar categorías
        List<Categoria> categorias = new ArrayList<>();
        for (Long categoriaId : dto.getCategoriasIds()) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categoría no encontrada con id: " + categoriaId));
            categorias.add(categoria);
        }

        // Crear libro
        Libro libro = new Libro();
        libro.setIsbn(dto.getIsbn());
        libro.setTitulo(dto.getTitulo());
        libro.setEditorial(dto.getEditorial());
        libro.setDescripcion(dto.getDescripcion());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setStockTotal(dto.getStockTotal());
        libro.setStockDisponible(dto.getStockTotal());
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setAutores(autores);
        libro.setCategorias(categorias);

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
    @Transactional
    public LibroResponseDTO actualizarLibro(Long id, LibroUpdateDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        // Actualizar campos básicos
        if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
            libro.setTitulo(dto.getTitulo());
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
        if (dto.getStockTotal() != null) {
            libro.setStockTotal(dto.getStockTotal());
            if (libro.getStockDisponible() > dto.getStockTotal()) {
                libro.setStockDisponible(dto.getStockTotal());
            }
        }

        // Actualizar autores si se proporcionan
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<Autor> nuevosAutores = new ArrayList<>();
            for (Long autorId : dto.getAutoresIds()) {
                Autor autor = autorRepository.findById(autorId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Autor no encontrado con id: " + autorId));
                nuevosAutores.add(autor);
            }
            libro.setAutores(nuevosAutores);
        }

        // Actualizar categorías si se proporcionan
        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {
            List<Categoria> nuevasCategorias = new ArrayList<>();
            for (Long categoriaId : dto.getCategoriasIds()) {
                Categoria categoria = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Categoría no encontrada con id: " + categoriaId));
                nuevasCategorias.add(categoria);
            }
            libro.setCategorias(nuevasCategorias);
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

    @Override
    public List<LibroResponseDTO> buscarLibrosPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorAutor(Long autorId) {
        if (!autorRepository.existsById(autorId)) {
            throw new ResourceNotFoundException("Autor no encontrado con id: " + autorId);
        }
        return libroRepository.findByAutorId(autorId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorAutorNombre(String nombre) {
        return libroRepository.findByAutorNombre(nombre).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + categoriaId);
        }
        return libroRepository.findByCategoriaId(categoriaId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponseDTO> buscarLibrosPorCategoriaCodigo(String codigo) {
        return libroRepository.findByCategoriaCodigo(codigo.toUpperCase()).stream()
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
    public List<LibroResponseDTO> busquedaAvanzada(String busqueda) {
        return libroRepository.busquedaAvanzada(busqueda).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    // Método helper para conversión
    private LibroResponseDTO convertirAResponseDTO(Libro libro) {
        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setId(libro.getId());
        dto.setIsbn(libro.getIsbn());
        dto.setTitulo(libro.getTitulo());
        dto.setEditorial(libro.getEditorial());
        dto.setDescripcion(libro.getDescripcion());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setEstado(libro.getEstado());
        dto.setStockTotal(libro.getStockTotal());
        dto.setStockDisponible(libro.getStockDisponible());
        dto.setFechaRegistro(libro.getFechaRegistro());
        dto.setFechaActualizacion(libro.getFechaActualizacion());
        dto.setDisponible(libro.estaDisponible());

        // Convertir autores
        List<AutorSimpleDTO> autoresDTO = libro.getAutores().stream()
                .map(autor -> new AutorSimpleDTO(
                        autor.getId(),
                        autor.getNombreCompleto(),
                        autor.getNacionalidad()))
                .collect(Collectors.toList());
        dto.setAutores(autoresDTO);

        // Convertir categorías
        List<CategoriaSimpleDTO> categoriasDTO = libro.getCategorias().stream()
                .map(categoria -> new CategoriaSimpleDTO(
                        categoria.getId(),
                        categoria.getCodigo(),
                        categoria.getNombre()))
                .collect(Collectors.toList());
        dto.setCategorias(categoriasDTO);

        return dto;
    }
}