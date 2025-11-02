package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuario(Usuario usuario);

    List<Prestamo> findByUsuarioId(Long usuarioId);

    List<Prestamo> findByLibro(Libro libro);

    List<Prestamo> findByLibroId(Long libroId);

    List<Prestamo> findByEstado(EstadoPrestamo estado);

    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, EstadoPrestamo estado);

    List<Prestamo> findByUsuarioIdAndEstado(Long usuarioId, EstadoPrestamo estado);

    List<Prestamo> findByLibroAndEstado(Libro libro, EstadoPrestamo estado);

    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionPrevista < :fecha")
    List<Prestamo> findPrestamosVencidos(LocalDateTime fecha);

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.estado = 'ACTIVO'")
    Long contarPrestamosActivosPorUsuario(Long usuarioId);

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId AND p.estado = 'ACTIVO'")
    Long contarPrestamosActivosPorLibro(Long libroId);
}