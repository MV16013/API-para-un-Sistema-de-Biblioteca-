package com.biblioteca.dto.prestamo;

import com.biblioteca.enums.EstadoPrestamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoSimpleResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long libroId;
    private String libroTitulo;
    private String libroAutor;
    private String libroIsbn;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionPrevista;
    private LocalDateTime fechaDevolucionReal;
    private EstadoPrestamo estado;
    private String observaciones;
    private BigDecimal multa;
    private Integer diasRetraso;
    private boolean vencido;
}