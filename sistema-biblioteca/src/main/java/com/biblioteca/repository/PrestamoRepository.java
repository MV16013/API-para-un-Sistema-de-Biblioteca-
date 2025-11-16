package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuario(Usuario usuario);

    List<Prestamo> findByUsuarioId(Long usuarioId);

    List<Prestamo> findByUsuarioIdOrderByFechaPrestamoDesc(Long usuarioId);

    List<Prestamo> findByLibro(Libro libro);

    List<Prestamo> findByLibroId(Long libroId);

    List<Prestamo> findByEstado(EstadoPrestamo estado);

    List<Prestamo> findByEstadoOrderByFechaDevolucionPrevistaAsc(EstadoPrestamo estado);

    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, EstadoPrestamo estado);

    List<Prestamo> findByUsuarioIdAndEstado(Long usuarioId, EstadoPrestamo estado);

    List<Prestamo> findByLibroAndEstado(Libro libro, EstadoPrestamo estado);

    List<Prestamo> findByLibroIdAndEstado(Long libroId, EstadoPrestamo estado);

    List<Prestamo> findByFechaPrestamoBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Prestamo> findByFechaDevolucionPrevistaBefore(LocalDateTime fecha);

    List<Prestamo> findByFechaDevolucionPrevistaAfter(LocalDateTime fecha);

    // Préstamos vencidos (activos con fecha de devolución pasada)
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionPrevista < :fecha")
    List<Prestamo> findPrestamosVencidos(@Param("fecha") LocalDateTime fecha);

    // Préstamos próximos a vencer (en los próximos X días)
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND " +
            "p.fechaDevolucionPrevista BETWEEN :ahora AND :limite")
    List<Prestamo> findPrestamosProximosAVencer(
            @Param("ahora") LocalDateTime ahora,
            @Param("limite") LocalDateTime limite);

    // Contar préstamos activos por usuario
    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.estado = 'ACTIVO'")
    Long contarPrestamosActivosPorUsuario(@Param("usuarioId") Long usuarioId);

    // Contar préstamos activos por libro
    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId AND p.estado = 'ACTIVO'")
    Long contarPrestamosActivosPorLibro(@Param("libroId") Long libroId);

    // Préstamos con multa
    @Query("SELECT p FROM Prestamo p WHERE p.multa > 0")
    List<Prestamo> findPrestamosConMulta();

    // Historial de préstamos de un libro
    @Query("SELECT p FROM Prestamo p WHERE p.libro.id = :libroId ORDER BY p.fechaPrestamo DESC")
    List<Prestamo> findHistorialPrestamosPorLibro(@Param("libroId") Long libroId);

    // Usuarios con más préstamos
    @Query("SELECT p.usuario, COUNT(p) as cantidad FROM Prestamo p " +
            "GROUP BY p.usuario ORDER BY cantidad DESC")
    List<Object[]> findUsuariosConMasPrestamos();

    // Libros más prestados en un período
    @Query("SELECT p.libro, COUNT(p) as cantidad FROM Prestamo p " +
            "WHERE p.fechaPrestamo BETWEEN :inicio AND :fin " +
            "GROUP BY p.libro ORDER BY cantidad DESC")
    List<Object[]> findLibrosMasPrestadosEnPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);
}