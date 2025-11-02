package com.biblioteca.entity;

import com.biblioteca.enums.EstadoLibro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 100)
    private String autor;

    @Column(length = 100)
    private String editorial;

    @Column(length = 1000)
    private String descripcion;

    private Integer anioPublicacion;

    @Column(length = 50)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoLibro estado;

    @Column(nullable = false)
    private Integer stockTotal;

    @Column(nullable = false)
    private Integer stockDisponible;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoLibro.DISPONIBLE;
        }
        if (stockDisponible == null) {
            stockDisponible = stockTotal;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Métodos de negocio
    public boolean estaDisponible() {
        return estado == EstadoLibro.DISPONIBLE && stockDisponible > 0;
    }

    public void reducirStock() {
        if (stockDisponible > 0) {
            stockDisponible--;
            if (stockDisponible == 0) {
                estado = EstadoLibro.PRESTADO;
            }
        } else {
            throw new IllegalStateException("No hay stock disponible para este libro");
        }
    }

    public void aumentarStock() {
        if (stockDisponible < stockTotal) {
            stockDisponible++;
            if (stockDisponible > 0 && estado == EstadoLibro.PRESTADO) {
                estado = EstadoLibro.DISPONIBLE;
            }
        }
    }
}