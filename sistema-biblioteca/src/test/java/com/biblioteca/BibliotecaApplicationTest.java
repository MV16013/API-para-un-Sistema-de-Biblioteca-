package com.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.sql.init.mode=never",
        "spring.jpa.defer-datasource-initialization=false"
})
class BibliotecaApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
        assertTrue(true);
    }

    @Test
    void testEjemploSimple() {
        int a = 2;
        int b = 3;
        assertTrue(a + b == 5);
    }
}