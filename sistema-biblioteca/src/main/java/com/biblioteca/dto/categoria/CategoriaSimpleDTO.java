package com.biblioteca.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSimpleDTO {
    private Long id;
    private String codigo;
    private String nombre;
}