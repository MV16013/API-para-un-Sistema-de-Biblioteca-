package com.biblioteca.dto.prestamo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoCreateDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "El ID del libro es obligatorio")
    @Positive(message = "El ID del libro debe ser positivo")
    private Long libroId;

    @NotNull(message = "Los días de préstamo son obligatorios")
    @Positive(message = "Los días de préstamo deben ser positivos")
    private Integer diasPrestamo;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
}