# API para un sistema de biblioteca
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
Estados/Enums: tipo_usuario, estado_usuario, estado_libro, estado_prestamo, estado_reserva, estado_multa.

# Diagrama de Clases Sistema de Gestión de Biblioteca
<img width="1957" height="881" alt="DiagramaClases" src="https://github.com/user-attachments/assets/e05443ab-0df6-431f-bbd8-52733a61836a" />

# Diagrama entidad relacion Sistema de gestion de biblioteca
<img width="3840" height="3279" alt="DiagramaEntidadRelacion" src="https://github.com/user-attachments/assets/a24bd280-5162-42ef-bf02-a2d359324b1c" />

## Casos de uso

1. Gestionar Usuarios
 - Descripción: El administrador registra, consulta y actualiza los datos de los
   usuarios (número identificación, nombre, email, teléfono, dirección, tipo y
   estado). El sistema valida duplicados y formatos.
   
2. Gestionar Libros
 - Descripción: El administrador registra libros (ISBN, título, editorial, idioma,
   año, páginas, stock). El sistema mantiene stock_disponible y el estado del
   libro (DISPONIBLE, MANTENIMIENTO, PERDIDO…).
   
3. Gestionar Autores
 - Descripción: El administrador registra y actualiza autores (nombre, apellido,
   nacionalidad, fecha nacimiento, biografía) y los vincula a libros.
   
4. Gestionar Categorías
 - Descripción: El administrador crea y mantiene categorías (nombre,
   descripción, código) y las asigna a libros para clasificar la colección.
   
5. Gestionar Préstamos
-  Descripción: El socio solicita un préstamo; el sistema verifica que el usuario
   esté activo y que el libro esté disponible, registra el préstamo (fecha prevista) y
   actualiza stock. Al devolver, registra la fecha real, marca el préstamo y restaura
   el stock. El sistema puede marcar préstamos vencidos o renovados y calcular
   multas.
   
6. Gestionar Reservas
 - Descripción: Si no hay stock disponible, el socio puede reservar un libro. El
   sistema crea una reserva con fecha de vencimiento; cuando el libro queda
   disponible, la reserva puede convertirse en préstamo o vencer/cancelarse.
   
7. Gestionar Multas
 - Descripción: El sistema genera multas por préstamos vencidos, calcula
   monto según días de retraso, registra estado
   (PENDIENTE/PAGADA/CANCELADA) y permite registrar pagos.
   
# Diagrama de casos de usos
![WhatsApp Image 2025-09-26 at 11 32 50 PM](https://github.com/user-attachments/assets/6e48cd32-59f3-46c5-9fcb-82e481c0bc2b)

## Tecnologías
- Java 17 con 
  
