package com.biblioteca.dto.libro;

import com.biblioteca.enums.EstadoLibro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String descripcion;
    private Integer anioPublicacion;
    private String categoria;
    private EstadoLibro estado;
    private Integer stockTotal;
    private Integer stockDisponible;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private boolean disponible;
}