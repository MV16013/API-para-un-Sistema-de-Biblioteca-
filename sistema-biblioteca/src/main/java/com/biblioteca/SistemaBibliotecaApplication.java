package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SistemaBibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SistemaBibliotecaApplication.class);
        Environment env = app.run(args).getEnvironment();

        String port = env.getProperty("server.port", "8080");
        String dbUrl = env.getProperty("spring.datasource.url", "No configurado");

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" Sistema de Biblioteca iniciado exitosamente");
        System.out.println(" Puerto: http://localhost:" + port);
        System.out.println(" Base de datos: " + dbUrl);
        System.out.println(" JPA ddl-auto: " + env.getProperty("spring.jpa.hibernate.ddl-auto"));
        System.out.println("=".repeat(60) + "\n");
    }
}