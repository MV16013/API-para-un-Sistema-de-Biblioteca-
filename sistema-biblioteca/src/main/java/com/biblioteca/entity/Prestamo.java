package com.biblioteca.entity;

import com.biblioteca.enums.EstadoPrestamo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(nullable = false)
    private LocalDateTime fechaDevolucionPrevista;

    private LocalDateTime fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPrestamo estado;

    @Column(length = 500)
    private String observaciones;

    @Column(precision = 10, scale = 2)
    private BigDecimal multa;

    @PrePersist
    protected void onCreate() {
        fechaPrestamo = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoPrestamo.ACTIVO;
        }
        multa = BigDecimal.ZERO;
    }

    // Métodos de negocio
    public Integer calcularDiasRetraso() {
        if (fechaDevolucionReal != null) {
            // Ya fue devuelto
            if (fechaDevolucionReal.isAfter(fechaDevolucionPrevista)) {
                return (int) ChronoUnit.DAYS.between(fechaDevolucionPrevista, fechaDevolucionReal);
            }
            return 0;
        } else {
            // Aún no se ha devuelto
            if (LocalDateTime.now().isAfter(fechaDevolucionPrevista)) {
                return (int) ChronoUnit.DAYS.between(fechaDevolucionPrevista, LocalDateTime.now());
            }
            return 0;
        }
    }

    public boolean estaVencido() {
        return estado == EstadoPrestamo.ACTIVO &&
                LocalDateTime.now().isAfter(fechaDevolucionPrevista);
    }

    public BigDecimal calcularMulta() {
        Integer diasRetraso = calcularDiasRetraso();
        if (diasRetraso > 0) {
            // $1.00 por día de retraso
            return BigDecimal.valueOf(diasRetraso);
        }
        return BigDecimal.ZERO;
    }

    public void marcarComoDevuelto() {
        this.fechaDevolucionReal = LocalDateTime.now();
        this.estado = EstadoPrestamo.DEVUELTO;
        this.multa = calcularMulta();
    }
}