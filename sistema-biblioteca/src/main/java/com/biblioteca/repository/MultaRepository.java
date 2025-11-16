package com.biblioteca.repository;

import com.biblioteca.entity.Multa;
import com.biblioteca.entity.Prestamo;
import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoMulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {

    List<Multa> findByUsuario(Usuario usuario);

    List<Multa> findByUsuarioId(Long usuarioId);

    List<Multa> findByUsuarioIdOrderByFechaGeneracionDesc(Long usuarioId);

    // ========== BÚSQUEDAS POR PRÉSTAMO ==========

    List<Multa> findByPrestamo(Prestamo prestamo);

    List<Multa> findByPrestamoId(Long prestamoId);

    // ========== BÚSQUEDAS POR ESTADO ==========

    List<Multa> findByEstado(EstadoMulta estado);

    List<Multa> findByEstadoOrderByFechaGeneracionDesc(EstadoMulta estado);

    // ========== BÚSQUEDAS COMBINADAS ==========

    List<Multa> findByUsuarioAndEstado(Usuario usuario, EstadoMulta estado);

    List<Multa> findByUsuarioIdAndEstado(Long usuarioId, EstadoMulta estado);

    // ========== BÚSQUEDAS POR MONTO ==========

    List<Multa> findByMontoGreaterThan(BigDecimal monto);

    List<Multa> findByMontoLessThan(BigDecimal monto);

    List<Multa> findByMontoBetween(BigDecimal min, BigDecimal max);

    // ========== BÚSQUEDAS POR FECHAS ==========

    List<Multa> findByFechaGeneracionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Multa> findByFechaPagoIsNull();

    List<Multa> findByFechaPagoIsNotNull();

    // ========== QUERIES PERSONALIZADAS ==========

    // Multas pendientes por usuario
    @Query("SELECT m FROM Multa m WHERE m.usuario.id = :usuarioId AND m.estado = 'PENDIENTE'")
    List<Multa> findMultasPendientesPorUsuario(@Param("usuarioId") Long usuarioId);

    // Calcular total de multas pendientes por usuario
    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Multa m " +
            "WHERE m.usuario.id = :usuarioId AND m.estado = 'PENDIENTE'")
    BigDecimal calcularTotalMultasPendientes(@Param("usuarioId") Long usuarioId);

    // Contar multas pendientes por usuario
    @Query("SELECT COUNT(m) FROM Multa m WHERE m.usuario.id = :usuarioId AND m.estado = 'PENDIENTE'")
    Long contarMultasPendientesPorUsuario(@Param("usuarioId") Long usuarioId);

    // Multas pagadas en un período
    @Query("SELECT m FROM Multa m WHERE m.estado = 'PAGADA' " +
            "AND m.fechaPago BETWEEN :inicio AND :fin")
    List<Multa> findMultasPagadasEnPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

    // Total recaudado en multas (pagadas)
    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Multa m WHERE m.estado = 'PAGADA'")
    BigDecimal calcularTotalRecaudado();

    // Total recaudado en un período
    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Multa m WHERE m.estado = 'PAGADA' " +
            "AND m.fechaPago BETWEEN :inicio AND :fin")
    BigDecimal calcularTotalRecaudadoEnPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

    // Usuarios con más multas
    @Query("SELECT m.usuario, COUNT(m) as cantidad FROM Multa m " +
            "WHERE m.estado = 'PENDIENTE' " +
            "GROUP BY m.usuario ORDER BY cantidad DESC")
    List<Object[]> findUsuariosConMasMultas();

    // Multas antiguas pendientes (más de X días)
    @Query("SELECT m FROM Multa m WHERE m.estado = 'PENDIENTE' " +
            "AND m.fechaGeneracion < :fecha")
    List<Multa> findMultasAntiguasPendientes(@Param("fecha") LocalDateTime fecha);

    // Estadísticas de multas por estado
    @Query("SELECT m.estado, COUNT(m), COALESCE(SUM(m.monto), 0) FROM Multa m GROUP BY m.estado")
    List<Object[]> findEstadisticasPorEstado();

    // Promedio de multas
    @Query("SELECT AVG(m.monto) FROM Multa m WHERE m.estado = 'PAGADA'")
    BigDecimal calcularPromedioMultas();
}
