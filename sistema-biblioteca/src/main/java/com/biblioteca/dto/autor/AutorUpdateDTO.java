package com.biblioteca.dto.autor;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorUpdateDTO {

    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;

    @Size(max = 100, message = "La nacionalidad no puede exceder 100 caracteres")
    private String nacionalidad;

    private LocalDate fechaNacimiento;

    @Size(max = 2000, message = "La biografía no puede exceder 2000 caracteres")
    private String biografia;
}
