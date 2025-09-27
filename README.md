# API-para-un-Sistema-de-Biblioteca-
Este repositorios tiene el objetivo de crear una API REST con Spring Boot para gestionar las operaciones de una biblioteca. Esto incluye el registro de libros y autores, así como la gestión de préstamos de libros para los socios, controlando el estado de los préstamos (activo, devuelto) y la disponibilidad de los libros.

## Objetivo
Implementar un sistema modular que permita:
- Registrar y administrar **Usuarios** (estados/tipos).
- Gestionar **Libros**, **Autores** y **Categorías**, con disponibilidad y stock.
- Administrar **Préstamos** y **Reservas** con sus estados y fechas clave.
- Generar y pagar **Multas** por retrasos.

## Modelo (resumen)
Entidades principales: Usuario, Libro, Autor, Categoria, Prestamo, Reserva, Multa.  
Estados/Enums: TipoUsuario, EstadoUsuario, EstadoLibro, EstadoPrestamo, EstadoReserva, EstadoMulta.

## Casos de uso
- Buscar/Reservar/Prestar/Devolver/Renovar libros.
- Gestionar catálogo y usuarios.
- Generar/Pagar multas.
- Reportes básicos.

## Tecnologías
- Java 17
  
