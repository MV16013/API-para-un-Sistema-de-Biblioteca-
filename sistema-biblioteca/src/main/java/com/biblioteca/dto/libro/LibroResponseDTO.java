package com.biblioteca.dto.libro;

import com.biblioteca.dto.autor.AutorSimpleDTO;
import com.biblioteca.dto.categoria.CategoriaSimpleDTO;
import com.biblioteca.enums.EstadoLibro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {
    private Long id;
    private String isbn;
    private String titulo;
    private String editorial;
    private String descripcion;
    private Integer anioPublicacion;
    private EstadoLibro estado;
    private Integer stockTotal;
    private Integer stockDisponible;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private boolean disponible;
    private List<AutorSimpleDTO> autores;
    private List<CategoriaSimpleDTO> categorias;
}