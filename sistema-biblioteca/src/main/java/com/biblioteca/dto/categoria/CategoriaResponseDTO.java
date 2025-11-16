package com.biblioteca.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private boolean valida;
}