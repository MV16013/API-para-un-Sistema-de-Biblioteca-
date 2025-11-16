package com.biblioteca.entity;

import com.biblioteca.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Many-to-One con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación Many-to-One con Libro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    @Column(nullable = false)
    private LocalDateTime fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva estado;

    @Column(length = 500)
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        fechaReserva = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.ACTIVA;
        }
        // Reserva válida por 48 horas (2 días)
        if (fechaVencimiento == null) {
            fechaVencimiento = LocalDateTime.now().plusDays(2);
        }
    }

    // Métodos de negocio
    public boolean estaVigente() {
        return estado == EstadoReserva.ACTIVA &&
                LocalDateTime.now().isBefore(fechaVencimiento);
    }

    public boolean estaVencida() {
        return estado == EstadoReserva.ACTIVA &&
                LocalDateTime.now().isAfter(fechaVencimiento);
    }

    public void marcarComoConvertida() {
        this.estado = EstadoReserva.CONVERTIDA;
    }

    public void marcarComoCancelada() {
        this.estado = EstadoReserva.CANCELADA;
    }

    public void marcarComoVencida() {
        this.estado = EstadoReserva.VENCIDA;
    }
}
