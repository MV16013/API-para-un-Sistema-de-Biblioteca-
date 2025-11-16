package com.biblioteca.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    // Relación Many-to-Many con Libro (lado inverso)
    @ManyToMany(mappedBy = "categorias")
    private List<Libro> libros = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Método de negocio
    public boolean esValida() {
        return nombre != null && !nombre.isBlank() &&
                codigo != null && !codigo.isBlank();
    }
}