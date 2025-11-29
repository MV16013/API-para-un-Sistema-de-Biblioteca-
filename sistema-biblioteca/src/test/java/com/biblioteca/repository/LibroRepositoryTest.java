package com.biblioteca.repository;

import com.biblioteca.entity.Libro;
import com.biblioteca.enums.EstadoLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setIsbn("TEST-1234");
        libro.setTitulo("Libro de Prueba");
        libro.setEditorial("Editorial Test");
        libro.setDescripcion("Descripción de prueba");
        libro.setAnioPublicacion(2024);
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setStockTotal(10);
        libro.setStockDisponible(10);
    }

    @Test
    void guardarLibroDebeGuardarCorrectamente() {
        Libro guardado = libroRepository.save(libro);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getIsbn()).isEqualTo("TEST-1234");
    }

    @Test
    void buscarPorIsbnDebeRetornarLibro() {
        libroRepository.save(libro);

        Optional<Libro> resultado = libroRepository.findByIsbn("TEST-1234");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitulo()).isEqualTo("Libro de Prueba");
    }

    @Test
    void actualizarLibroDebeCambiarTitulo() {
        Libro guardado = libroRepository.save(libro);

        guardado.setTitulo("Nuevo Título Actualizado");
        Libro actualizado = libroRepository.save(guardado);

        assertThat(actualizado.getTitulo()).isEqualTo("Nuevo Título Actualizado");
    }

    @Test
    void eliminarLibroDebeEliminarCorrectamente() {
        Libro guardado = libroRepository.save(libro);

        libroRepository.deleteById(guardado.getId());

        Optional<Libro> resultado = libroRepository.findById(guardado.getId());

        assertThat(resultado).isNotPresent();
    }
}
