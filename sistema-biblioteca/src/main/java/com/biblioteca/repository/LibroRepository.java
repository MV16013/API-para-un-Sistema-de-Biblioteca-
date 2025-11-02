package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.enums.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByAutorContainingIgnoreCase(String autor);

    List<Libro> findByEstado(EstadoLibro estado);

    List<Libro> findByCategoria(String categoria);

    boolean existsByIsbn(String isbn);

    List<Libro> findByStockDisponibleGreaterThan(Integer stock);
}