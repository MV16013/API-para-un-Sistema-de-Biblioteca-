package com.biblioteca.dto.multa;

import com.biblioteca.enums.EstadoMulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultaResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long prestamoId;
    private BigDecimal monto;
    private String concepto;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaPago;
    private EstadoMulta estado;
    private boolean pagada;
    private boolean pendiente;
}