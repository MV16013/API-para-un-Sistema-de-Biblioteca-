package com.biblioteca.dto.usuario;

import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String numeroIdentificacion;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
    private TipoUsuario tipoUsuario;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}