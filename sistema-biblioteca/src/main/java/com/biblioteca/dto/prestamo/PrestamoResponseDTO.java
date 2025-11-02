package com.biblioteca.dto.prestamo;

import com.biblioteca.dto.libro.LibroResponseDTO;
import com.biblioteca.dto.usuario.UsuarioResponseDTO;
import com.biblioteca.enums.EstadoPrestamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponseDTO {
    private Long id;
    private UsuarioResponseDTO usuario;
    private LibroResponseDTO libro;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionPrevista;
    private LocalDateTime fechaDevolucionReal;
    private EstadoPrestamo estado;
    private String observaciones;
    private BigDecimal multa;
    private Integer diasRetraso;
    private boolean vencido;
}