package com.biblioteca.entity;

import com.biblioteca.enums.EstadoUsuario;
import com.biblioteca.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNumeroIdentificacion("12345678");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@email.com");
        usuario.setTelefono("+1234567890");
        usuario.setDireccion("Calle Principal 123");
        usuario.setTipoUsuario(TipoUsuario.ESTUDIANTE);
        usuario.setEstado(EstadoUsuario.ACTIVO);
    }

    @Test
    void testCreacionUsuario() {
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("12345678", usuario.getNumeroIdentificacion());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("juan.perez@email.com", usuario.getEmail());
        assertEquals("+1234567890", usuario.getTelefono());
        assertEquals("Calle Principal 123", usuario.getDireccion());
        assertEquals(TipoUsuario.ESTUDIANTE, usuario.getTipoUsuario());
        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
    }

    @Test
    void testGetNombreCompleto() {
        assertEquals("Juan Pérez", usuario.getNombreCompleto());
    }


    @Test
    void testPuedePrestar() {
        // Usuario ACTIVO puede prestar
        usuario.setEstado(EstadoUsuario.ACTIVO);
        assertTrue(usuario.puedePrestar());

        // Usuario INACTIVO no puede prestar
        usuario.setEstado(EstadoUsuario.INACTIVO);
        assertFalse(usuario.puedePrestar());

        // Usuario SUSPENDIDO no puede prestar
        usuario.setEstado(EstadoUsuario.SUSPENDIDO);
        assertFalse(usuario.puedePrestar());

    }

    @Test
    void testPrePersist() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNumeroIdentificacion("87654321");
        nuevoUsuario.setNombre("Maria");
        nuevoUsuario.setApellido("Gomez");
        nuevoUsuario.setEmail("maria@email.com");
        nuevoUsuario.setTipoUsuario(TipoUsuario.PROFESOR);

        nuevoUsuario.onCreate();

        assertNotNull(nuevoUsuario.getFechaRegistro());
        assertEquals(EstadoUsuario.ACTIVO, nuevoUsuario.getEstado());
    }

    @Test
    void testPrePersistConEstadoDefinido() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNumeroIdentificacion("87654321");
        nuevoUsuario.setNombre("Maria");
        nuevoUsuario.setApellido("Gomez");
        nuevoUsuario.setEmail("maria@email.com");
        nuevoUsuario.setTipoUsuario(TipoUsuario.PROFESOR);
        nuevoUsuario.setEstado(EstadoUsuario.SUSPENDIDO); // Estado explícito

        nuevoUsuario.onCreate();

        assertNotNull(nuevoUsuario.getFechaRegistro());
        assertEquals(EstadoUsuario.SUSPENDIDO, nuevoUsuario.getEstado()); // No debe cambiar
    }

    @Test
    void testPreUpdate() {
        LocalDateTime fechaOriginal = usuario.getFechaActualizacion();

        usuario.onUpdate();

        assertNotNull(usuario.getFechaActualizacion());
        assertNotEquals(fechaOriginal, usuario.getFechaActualizacion());
    }

    @Test
    void testRelacionConPrestamos() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);

        usuario.setPrestamos(new ArrayList<>());
        usuario.getPrestamos().add(prestamo);

        prestamo.setUsuario(usuario);

        assertFalse(usuario.getPrestamos().isEmpty());
        assertEquals(1, usuario.getPrestamos().size());
        assertEquals(prestamo, usuario.getPrestamos().get(0));
        assertEquals(usuario, prestamo.getUsuario());
    }

    @Test
    void testRelacionConReservas() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);

        usuario.setReservas(new ArrayList<>());
        usuario.getReservas().add(reserva);

        reserva.setUsuario(usuario);

        assertFalse(usuario.getReservas().isEmpty());
        assertEquals(1, usuario.getReservas().size());
        assertEquals(reserva, usuario.getReservas().get(0));
        assertEquals(usuario, reserva.getUsuario());
    }

    @Test
    void testRelacionConMultas() {
        Multa multa = new Multa();
        multa.setId(1L);

        usuario.setMultas(new ArrayList<>());
        usuario.getMultas().add(multa);

        multa.setUsuario(usuario);

        assertFalse(usuario.getMultas().isEmpty());
        assertEquals(1, usuario.getMultas().size());
        assertEquals(multa, usuario.getMultas().get(0));
        assertEquals(usuario, multa.getUsuario());
    }

    @Test
    void testAgregarPrestamo() {
        Prestamo prestamo = new Prestamo();
        usuario.setPrestamos(new ArrayList<>());

        usuario.getPrestamos().add(prestamo);
        prestamo.setUsuario(usuario);

        assertEquals(1, usuario.getPrestamos().size());
        assertEquals(usuario, prestamo.getUsuario());
    }

    @Test
    void testRemoverPrestamo() {
        Prestamo prestamo = new Prestamo();
        usuario.setPrestamos(new ArrayList<>());
        usuario.getPrestamos().add(prestamo);

        usuario.getPrestamos().remove(prestamo);
        prestamo.setUsuario(null);

        assertTrue(usuario.getPrestamos().isEmpty());
        assertNull(prestamo.getUsuario());
    }

    @Test
    void testEqualsYHashCode() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNumeroIdentificacion("12345");

        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        usuario2.setNumeroIdentificacion("12345");

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(usuario.toString());
        assertTrue(usuario.toString().contains("Juan"));
        assertTrue(usuario.toString().contains("Pérez"));
    }

    @Test
    void testConstructorCompleto() {
        List<Prestamo> prestamos = new ArrayList<>();
        List<Reserva> reservas = new ArrayList<>();
        List<Multa> multas = new ArrayList<>();

        Usuario usuarioCompleto = new Usuario(
                2L,
                "87654321",
                "Ana",
                "Garcia",
                "ana.garcia@email.com",
                "+0987654321",
                "Avenida Siempre Viva 742",
                TipoUsuario.ADMINISTRATIVO,
                EstadoUsuario.ACTIVO,
                LocalDateTime.now(),
                LocalDateTime.now(),
                prestamos,
                reservas,
                multas
        );

        assertEquals("Ana", usuarioCompleto.getNombre());
        assertEquals("Garcia", usuarioCompleto.getApellido());
        assertEquals("Ana Garcia", usuarioCompleto.getNombreCompleto());
        assertEquals(TipoUsuario.ADMINISTRATIVO, usuarioCompleto.getTipoUsuario());
        assertTrue(usuarioCompleto.puedePrestar());
    }

    @Test
    void testDiferentesTiposUsuario() {
        // Test para ESTUDIANTE
        usuario.setTipoUsuario(TipoUsuario.ESTUDIANTE);
        assertEquals(TipoUsuario.ESTUDIANTE, usuario.getTipoUsuario());

        // Test para PROFESOR
        usuario.setTipoUsuario(TipoUsuario.PROFESOR);
        assertEquals(TipoUsuario.PROFESOR, usuario.getTipoUsuario());

        // Test para ADMINISTRATIVO
        usuario.setTipoUsuario(TipoUsuario.ADMINISTRATIVO);
        assertEquals(TipoUsuario.ADMINISTRATIVO, usuario.getTipoUsuario());

    }

    @Test
    void testDiferentesEstadosUsuario() {
        // Test para ACTIVO
        usuario.setEstado(EstadoUsuario.ACTIVO);
        assertTrue(usuario.puedePrestar());

        // Test para INACTIVO
        usuario.setEstado(EstadoUsuario.INACTIVO);
        assertFalse(usuario.puedePrestar());

        // Test para SUSPENDIDO
        usuario.setEstado(EstadoUsuario.SUSPENDIDO);
        assertFalse(usuario.puedePrestar());

    }
}
