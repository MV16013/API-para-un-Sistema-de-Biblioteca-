package com.biblioteca.dto.reserva;

import com.biblioteca.enums.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long libroId;
    private String libroTitulo;
    private String libroIsbn;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaVencimiento;
    private EstadoReserva estado;
    private String observaciones;
    private boolean vigente;
    private boolean vencida;
}