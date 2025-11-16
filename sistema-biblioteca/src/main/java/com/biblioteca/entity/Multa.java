package com.biblioteca.entity;

import com.biblioteca.enums.EstadoMulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "multas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Many-to-One con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación Many-to-One con Préstamo (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, length = 500)
    private String concepto;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoMulta estado;

    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoMulta.PENDIENTE;
        }
    }

    // Métodos de negocio
    public boolean esPagada() {
        return estado == EstadoMulta.PAGADA;
    }

    public boolean estaPendiente() {
        return estado == EstadoMulta.PENDIENTE;
    }

    public void marcarComoPagada() {
        this.estado = EstadoMulta.PAGADA;
        this.fechaPago = LocalDateTime.now();
    }

    public void marcarComoCancelada() {
        this.estado = EstadoMulta.CANCELADA;
    }

    public boolean bloqueaPrestamos() {
        return estado == EstadoMulta.PENDIENTE;
    }
}