package com.biblioteca.dto.libro;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroUpdateDTO {

    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @Size(max = 100, message = "El autor no puede exceder 100 caracteres")
    private String autor;

    @Size(max = 100, message = "La editorial no puede exceder 100 caracteres")
    private String editorial;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    private Integer anioPublicacion;

    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;

    @Positive(message = "El stock total debe ser positivo")
    private Integer stockTotal;
}