package com.biblioteca.entity;

import com.biblioteca.enums.EstadoLibro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String editorial;

    @Column(length = 1000)
    private String descripcion;

    private Integer anioPublicacion;

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

    // Relación Many-to-Many con Autor (lado propietario)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    // Relación Many-to-Many con Categoría (lado propietario)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libro_categoria",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    // Relación One-to-Many con Préstamo
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<>();

    // Relación One-to-Many con Reserva
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

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

    public void agregarAutor(Autor autor) {
        if (!this.autores.contains(autor)) {
            this.autores.add(autor);
            autor.getLibros().add(this);
        }
    }

    public void removerAutor(Autor autor) {
        this.autores.remove(autor);
        autor.getLibros().remove(this);
    }

    public void agregarCategoria(Categoria categoria) {
        if (!this.categorias.contains(categoria)) {
            this.categorias.add(categoria);
            categoria.getLibros().add(this);
        }
    }

    public void removerCategoria(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.getLibros().remove(this);
    }
}