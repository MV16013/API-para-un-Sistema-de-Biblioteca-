package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.enums.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    //List<Libro> findByAutorContainingIgnoreCase(String autor);

    List<Libro> findByEstado(EstadoLibro estado);

    List<Libro> findByAnioPublicacion(Integer anio);

    List<Libro> findByStockDisponibleGreaterThan(Integer stock);

    List<Libro> findByStockDisponibleEquals(Integer stock);

    boolean existsByIsbn(String isbn);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autores a WHERE a.id = :autorId")
    List<Libro> findByAutorId(@Param("autorId") Long autorId);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autores a WHERE " +
            "LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
            "LOWER(a.apellido) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Libro> findByAutorNombre(@Param("nombre") String nombre);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.categorias c WHERE c.id = :categoriaId")
    List<Libro> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.categorias c WHERE c.codigo = :codigo")
    List<Libro> findByCategoriaCodigo(@Param("codigo") String codigo);

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.categorias c WHERE " +
            "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Libro> findByCategoriaNombre(@Param("nombre") String nombre);

    // Libros más prestados
    @Query("SELECT l, COUNT(p) as cantidad FROM Libro l JOIN l.prestamos p " +
            "GROUP BY l ORDER BY cantidad DESC")
    List<Object[]> findLibrosMasPrestados();

    // Libros nunca prestados
    @Query("SELECT l FROM Libro l WHERE l.prestamos IS EMPTY")
    List<Libro> findLibrosNuncaPrestados();

    // Libros con préstamos vencidos
    @Query("SELECT DISTINCT l FROM Libro l JOIN l.prestamos p " +
            "WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionPrevista < CURRENT_TIMESTAMP")
    List<Libro> findLibrosConPrestamosVencidos();

    // Libros disponibles por categoría
    @Query("SELECT DISTINCT l FROM Libro l JOIN l.categorias c " +
            "WHERE c.id = :categoriaId AND l.stockDisponible > 0")
    List<Libro> findLibrosDisponiblesPorCategoria(@Param("categoriaId") Long categoriaId);

    // Búsqueda avanzada (título, autor, categoría)
    @Query("SELECT DISTINCT l FROM Libro l " +
            "LEFT JOIN l.autores a " +
            "LEFT JOIN l.categorias c " +
            "WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(a.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(a.apellido) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Libro> busquedaAvanzada(@Param("busqueda") String busqueda);
}