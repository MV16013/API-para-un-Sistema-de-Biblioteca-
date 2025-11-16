package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.entity.Reserva;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario(Usuario usuario);

    List<Reserva> findByUsuarioId(Long usuarioId);

    List<Reserva> findByUsuarioIdOrderByFechaReservaDesc(Long usuarioId);

    // ========== BÚSQUEDAS POR LIBRO ==========

    List<Reserva> findByLibro(Libro libro);

    List<Reserva> findByLibroId(Long libroId);

    // ========== BÚSQUEDAS POR ESTADO ==========

    List<Reserva> findByEstado(EstadoReserva estado);

    List<Reserva> findByEstadoOrderByFechaVencimientoAsc(EstadoReserva estado);

    // ========== BÚSQUEDAS COMBINADAS ==========

    List<Reserva> findByUsuarioAndEstado(Usuario usuario, EstadoReserva estado);

    List<Reserva> findByUsuarioIdAndEstado(Long usuarioId, EstadoReserva estado);

    List<Reserva> findByLibroAndEstado(Libro libro, EstadoReserva estado);

    List<Reserva> findByLibroIdAndEstado(Long libroId, EstadoReserva estado);

    // ========== BÚSQUEDAS POR FECHAS ==========

    List<Reserva> findByFechaReservaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Reserva> findByFechaVencimientoBefore(LocalDateTime fecha);

    List<Reserva> findByFechaVencimientoAfter(LocalDateTime fecha);

    // ========== QUERIES PERSONALIZADAS ==========

    // Reservas vencidas (activas con fecha de vencimiento pasada)
    @Query("SELECT r FROM Reserva r WHERE r.estado = 'ACTIVA' AND r.fechaVencimiento < :fecha")
    List<Reserva> findReservasVencidas(@Param("fecha") LocalDateTime fecha);

    // Reservas próximas a vencer (en las próximas X horas)
    @Query("SELECT r FROM Reserva r WHERE r.estado = 'ACTIVA' AND " +
            "r.fechaVencimiento BETWEEN :ahora AND :limite")
    List<Reserva> findReservasProximasAVencer(
            @Param("ahora") LocalDateTime ahora,
            @Param("limite") LocalDateTime limite);

    // Contar reservas activas por usuario
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario.id = :usuarioId AND r.estado = 'ACTIVA'")
    Long contarReservasActivasPorUsuario(@Param("usuarioId") Long usuarioId);

    // Contar reservas activas por libro
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.libro.id = :libroId AND r.estado = 'ACTIVA'")
    Long contarReservasActivasPorLibro(@Param("libroId") Long libroId);

    // Verificar si usuario ya tiene reserva activa del libro
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.usuario.id = :usuarioId " +
            "AND r.libro.id = :libroId AND r.estado = 'ACTIVA'")
    boolean existeReservaActivaUsuarioLibro(
            @Param("usuarioId") Long usuarioId,
            @Param("libroId") Long libroId);

    // Libros más reservados
    @Query("SELECT r.libro, COUNT(r) as cantidad FROM Reserva r " +
            "GROUP BY r.libro ORDER BY cantidad DESC")
    List<Object[]> findLibrosMasReservados();

    // Estadísticas de reservas por estado
    @Query("SELECT r.estado, COUNT(r) FROM Reserva r GROUP BY r.estado")
    List<Object[]> findEstadisticasPorEstado();
}