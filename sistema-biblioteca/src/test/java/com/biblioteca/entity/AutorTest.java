package com.biblioteca.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    private Autor autor;
    private Libro libro;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Gabriel");
        autor.setApellido("García Márquez");
        autor.setNacionalidad("Colombiana");
        autor.setFechaNacimiento(LocalDate.of(1927, 3, 6));
        autor.setBiografia("Escritor, guionista, editor y periodista colombiano.");

        libro = new Libro();
        libro.setId(1L);
        libro.setIsbn("978-1234567890");
        libro.setTitulo("Cien años de soledad");
    }

    @Test
    void testCreacionAutor() {
        assertNotNull(autor);
        assertEquals(1L, autor.getId());
        assertEquals("Gabriel", autor.getNombre());
        assertEquals("García Márquez", autor.getApellido());
        assertEquals("Colombiana", autor.getNacionalidad());
        assertEquals(LocalDate.of(1927, 3, 6), autor.getFechaNacimiento());
        assertNotNull(autor.getBiografia());
    }

    @Test
    void testGetNombreCompleto() {
        assertEquals("Gabriel García Márquez", autor.getNombreCompleto());
    }

    @Test
    void testPrePersist() {
        Autor nuevoAutor = new Autor();
        nuevoAutor.setNombre("Test");
        nuevoAutor.setApellido("Autor");

        nuevoAutor.onCreate();

        assertNotNull(nuevoAutor.getFechaRegistro());
        assertTrue(nuevoAutor.getFechaRegistro().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testPreUpdate() {
        LocalDateTime fechaOriginal = autor.getFechaActualizacion();

        autor.onUpdate();

        assertNotNull(autor.getFechaActualizacion());
        assertNotEquals(fechaOriginal, autor.getFechaActualizacion());
    }

    @Test
    void testRelacionConLibros() {
        // Configurar la relación bidireccional
        autor.setLibros(new ArrayList<>());
        autor.getLibros().add(libro);

        libro.setAutores(new ArrayList<>());
        libro.getAutores().add(autor);

        assertFalse(autor.getLibros().isEmpty());
        assertEquals(1, autor.getLibros().size());
        assertEquals("Cien años de soledad", autor.getLibros().get(0).getTitulo());

        assertFalse(libro.getAutores().isEmpty());
        assertEquals("Gabriel García Márquez", libro.getAutores().get(0).getNombreCompleto());
    }

    @Test
    void testAgregarLibro() {
        autor.setLibros(new ArrayList<>());
        autor.getLibros().add(libro);

        assertEquals(1, autor.getLibros().size());
        assertEquals(libro, autor.getLibros().get(0));
    }

    @Test
    void testRemoverLibro() {
        autor.setLibros(new ArrayList<>());
        autor.getLibros().add(libro);

        autor.getLibros().remove(libro);

        assertTrue(autor.getLibros().isEmpty());
    }

    @Test
    void testEqualsYHashCode() {
        Autor autor1 = new Autor();
        autor1.setId(1L);
        autor1.setNombre("Autor");
        autor1.setApellido("Test");

        Autor autor2 = new Autor();
        autor2.setId(1L);
        autor2.setNombre("Autor");
        autor2.setApellido("Test");

        assertEquals(autor1, autor2);
        assertEquals(autor1.hashCode(), autor2.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(autor.toString());
        assertTrue(autor.toString().contains("Gabriel"));
        assertTrue(autor.toString().contains("García Márquez"));
    }

    @Test
    void testConstructorCompleto() {
        List<Libro> libros = new ArrayList<>();
        libros.add(libro);

        Autor autorCompleto = new Autor(
                2L,
                "Isabel",
                "Allende",
                "Chilena",
                LocalDate.of(1942, 8, 2),
                "Escritora chilena",
                LocalDateTime.now(),
                LocalDateTime.now(),
                libros
        );

        assertEquals("Isabel", autorCompleto.getNombre());
        assertEquals("Allende", autorCompleto.getApellido());
        assertEquals("Isabel Allende", autorCompleto.getNombreCompleto());
        assertFalse(autorCompleto.getLibros().isEmpty());
    }
}

