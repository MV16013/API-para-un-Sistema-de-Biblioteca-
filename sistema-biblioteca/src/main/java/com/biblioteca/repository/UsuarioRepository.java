package com.biblioteca.repository;

import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNumeroIdentificacion(String numeroIdentificacion);

    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    List<Usuario> findByEstado(EstadoUsuario estado);

    List<Usuario> findByTipoUsuarioAndEstado(TipoUsuario tipoUsuario, EstadoUsuario estado);

    List<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    boolean existsByEmail(String email);

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    // Buscar usuarios con préstamos activos
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.prestamos p WHERE p.estado = 'ACTIVO'")
    List<Usuario> findUsuariosConPrestamosActivos();

    // Buscar usuarios con multas pendientes
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.multas m WHERE m.estado = 'PENDIENTE'")
    List<Usuario> findUsuariosConMultasPendientes();

    // Contar préstamos activos de un usuario
    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.estado = 'ACTIVO'")
    Long contarPrestamosActivos(@Param("usuarioId") Long usuarioId);

    // Contar multas pendientes de un usuario
    @Query("SELECT COUNT(m) FROM Multa m WHERE m.usuario.id = :usuarioId AND m.estado = 'PENDIENTE'")
    Long contarMultasPendientes(@Param("usuarioId") Long usuarioId);

}