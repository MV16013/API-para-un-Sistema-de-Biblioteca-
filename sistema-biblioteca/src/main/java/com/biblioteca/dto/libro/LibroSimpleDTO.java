package com.biblioteca.dto.libro;

import com.biblioteca.enums.EstadoLibro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroSimpleDTO {
    private Long id;
    private String isbn;
    private String titulo;
    private EstadoLibro estado;
    private Integer stockDisponible;
}