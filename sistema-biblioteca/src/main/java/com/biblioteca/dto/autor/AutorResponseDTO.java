package com.biblioteca.dto.autor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String biografia;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
