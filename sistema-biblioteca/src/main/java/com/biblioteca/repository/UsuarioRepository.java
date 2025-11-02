package com.biblioteca.repository;

import com.biblioteca.entity.Usuario;
import com.biblioteca.enums.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNumeroIdentificacion(String numeroIdentificacion);

    List<Usuario> findByEstado(EstadoUsuario estado);

    boolean existsByEmail(String email);

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}