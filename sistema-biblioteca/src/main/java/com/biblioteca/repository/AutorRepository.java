package com.biblioteca.repository;

import com.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Buscar por nombre (case insensitive)
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por apellido (case insensitive)
    List<Autor> findByApellidoContainingIgnoreCase(String apellido);

    // Buscar por nombre o apellido (case insensitive)
    List<Autor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);

    // Buscar por nacionalidad
    List<Autor> findByNacionalidad(String nacionalidad);

    // Buscar por nacionalidad (case insensitive)
    List<Autor> findByNacionalidadContainingIgnoreCase(String nacionalidad);

    // ========== QUERIES PERSONALIZADAS CON @Query ==========

    // Buscar autores con libros disponibles
    @Query("SELECT DISTINCT a FROM Autor a JOIN a.libros l WHERE l.stockDisponible > 0")
    List<Autor> findAutoresConLibrosDisponibles();

    // Contar libros de un autor
    @Query("SELECT COUNT(l) FROM Libro l JOIN l.autores a WHERE a.id = :autorId")
    Long contarLibrosPorAutor(@Param("autorId") Long autorId);

    // Buscar autores por libro
    @Query("SELECT a FROM Autor a JOIN a.libros l WHERE l.id = :libroId")
    List<Autor> findByLibroId(@Param("libroId") Long libroId);

    // Buscar autores más prolíficos (con más libros)
    @Query("SELECT a, COUNT(l) as cantidad FROM Autor a JOIN a.libros l " +
            "GROUP BY a ORDER BY cantidad DESC")
    List<Object[]> findAutoresMasProlíficos();
}
