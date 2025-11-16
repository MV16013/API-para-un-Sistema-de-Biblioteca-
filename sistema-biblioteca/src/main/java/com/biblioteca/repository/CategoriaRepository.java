package com.biblioteca.repository;

import com.biblioteca.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Buscar por código (único)
    Optional<Categoria> findByCodigo(String codigo);

    // Buscar por nombre (case insensitive)
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    // Buscar ordenadas por nombre
    List<Categoria> findAllByOrderByNombreAsc();

    // ========== VALIDACIONES DE EXISTENCIA ==========

    boolean existsByCodigo(String codigo);

    // ========== QUERIES PERSONALIZADAS CON @Query ==========

    // Buscar categorías con libros disponibles
    @Query("SELECT DISTINCT c FROM Categoria c JOIN c.libros l WHERE l.stockDisponible > 0")
    List<Categoria> findCategoriasConLibrosDisponibles();

    // Contar libros por categoría
    @Query("SELECT COUNT(l) FROM Libro l JOIN l.categorias c WHERE c.id = :categoriaId")
    Long contarLibrosPorCategoria(@Param("categoriaId") Long categoriaId);

    // Buscar categorías por libro
    @Query("SELECT c FROM Categoria c JOIN c.libros l WHERE l.id = :libroId")
    List<Categoria> findByLibroId(@Param("libroId") Long libroId);

    // Categorías más populares (con más libros)
    @Query("SELECT c, COUNT(l) as cantidad FROM Categoria c JOIN c.libros l " +
            "GROUP BY c ORDER BY cantidad DESC")
    List<Object[]> findCategoriasMasPopulares();
}
