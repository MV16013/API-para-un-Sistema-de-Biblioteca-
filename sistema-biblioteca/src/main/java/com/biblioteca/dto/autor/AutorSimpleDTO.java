package com.biblioteca.dto.autor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorSimpleDTO {
    private Long id;
    private String nombreCompleto;
    private String nacionalidad;
}
