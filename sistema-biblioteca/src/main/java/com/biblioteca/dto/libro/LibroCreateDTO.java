package com.biblioteca.dto.libro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroCreateDTO {

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(max = 20, message = "El ISBN no puede exceder 20 caracteres")
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
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

    @NotNull(message = "El stock total es obligatorio")
    @Positive(message = "El stock total debe ser positivo")
    private Integer stockTotal;
}
