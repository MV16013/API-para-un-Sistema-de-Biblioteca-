package com.biblioteca.entity;

import com.biblioteca.enums.EstadoLibro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    private Libro libro;
    private Autor autor;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setId(1L);
        libro.setIsbn("978-1234567890");
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial("Editorial Sudamericana");
        libro.setDescripcion("Una novela del realismo mágico");
        libro.setAnioPublicacion(1967);
        libro.setEstado(EstadoLibro.DISPONIBLE); // ESTADO EXPLÍCITO
        libro.setStockTotal(5);
        libro.setStockDisponible(3);

        autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Gabriel García Márquez");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Realismo Mágico");
    }

    @Test
    void testEstaDisponible() {
        // Configuración inicial explícita
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setStockDisponible(3);

        assertTrue(libro.estaDisponible(),
                "Libro con estado DISPONIBLE y stock > 0 debería estar disponible");
    }

    @Test
    void testEstaDisponibleCasosCompletos() {
        // Caso 1: Disponible
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setStockDisponible(1);
        assertTrue(libro.estaDisponible());

        // Caso 2: No disponible por stock
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setStockDisponible(0);
        assertFalse(libro.estaDisponible());

        // Caso 3: No disponible por estado
        libro.setEstado(EstadoLibro.PRESTADO);
        libro.setStockDisponible(5);
        assertFalse(libro.estaDisponible());

    }

    // Los otros tests permanecen igual...
    @Test
    void testCreacionLibro() {
        assertNotNull(libro);
        assertEquals(1L, libro.getId());
        assertEquals("978-1234567890", libro.getIsbn());
        assertEquals("Cien años de soledad", libro.getTitulo());
        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado()); // Verificar estado
        assertEquals(3, libro.getStockDisponible());
    }

    @Test
    void testPrePersist() {
        Libro nuevoLibro = new Libro();
        nuevoLibro.setStockTotal(3);

        nuevoLibro.onCreate();

        assertNotNull(nuevoLibro.getFechaRegistro());
        assertEquals(EstadoLibro.DISPONIBLE, nuevoLibro.getEstado());
        assertEquals(3, nuevoLibro.getStockDisponible());
    }

    @Test
    void testReducirStock() {
        int stockInicial = libro.getStockDisponible();

        libro.reducirStock();

        assertEquals(stockInicial - 1, libro.getStockDisponible());
    }

    @Test
    void testReducirStockCambiaEstadoAPrestado() {
        libro.setStockDisponible(1);

        libro.reducirStock();

        assertEquals(EstadoLibro.PRESTADO, libro.getEstado());
        assertEquals(0, libro.getStockDisponible());
    }

    @Test
    void testAumentarStock() {
        libro.setStockDisponible(2);
        libro.setEstado(EstadoLibro.PRESTADO);

        libro.aumentarStock();

        assertEquals(3, libro.getStockDisponible());
        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado());
    }
}