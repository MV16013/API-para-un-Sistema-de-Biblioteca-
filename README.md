# API-para-un-Sistema-de-Biblioteca-
Este repositorios tiene el objetivo de crear una API REST con Spring Boot para gestionar las operaciones de una biblioteca. Esto incluye el registro de libros y autores, así como la gestión de préstamos de libros para los socios, controlando el estado de los préstamos (activo, devuelto) y la disponibilidad de los libros.

## Objetivo
Implementar un sistema modular que permita:
- Registrar y administrar **Usuarios** (estados/tipos).
- Gestionar **Libros**, **Autores** y **Categorías**, con disponibilidad y stock.
- Administrar **Préstamos** y **Reservas** con sus estados y fechas clave.
- Generar y pagar **Multas** por retrasos.
- 
## Modelo (resumen)
Entidades principales: Usuario, Libro, Autor, Categoria, Prestamo, Reserva, Multa.  
Estados/Enums: TipoUsuario, EstadoUsuario, EstadoLibro, EstadoPrestamo, EstadoReserva, EstadoMulta.

<img width="3840" height="3279" alt="DiagramaEntidadRelacion" src="https://github.com/user-attachments/assets/a24bd280-5162-42ef-bf02-a2d359324b1c" />

## Casos de uso
- Buscar/Reservar/Prestar/Devolver/Renovar libros.
- Gestionar catálogo y usuarios.
- Generar/Pagar multas.
- Reportes básicos.

![WhatsApp Image 2025-09-26 at 11 32 50 PM](https://github.com/user-attachments/assets/6e48cd32-59f3-46c5-9fcb-82e481c0bc2b)

## Tecnologías
- Java 17
  
