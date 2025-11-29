<div align="center">

<!-- Logo de la Universidad -->
<img src="https://logowik.com/content/uploads/images/universidad-de-el-salvador1775.logowik.com.webp" alt="Universidad de El Salvador" width="200"/>

# API para un Sistema de Biblioteca
### Proyecto Final -  Programación Orientada a Objetos 

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-FCC624?style=for-the-badge&logo=PostgreSQL&logoColor=black)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2496ED?style=for-the-badge&logo=SpringBoot&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

**Universidad de El Salvador**  
**Facultad Multidisciplinaria de Occidente**  
**Departamento de Ingeniería y Arquitectura**  
**Ingenieria en Desarollo de Software**

</div>

---


## Tabla de Contenidos

- [Descripción General](#-DescripciónGeneral)
- [Equipo de Desarollo](#-Equipo_de_Desarollo)
- [Objetivos](#-Objetivos)
- [Arquitectura y Estructura](#-Arquitectura_y_Estructura)
- [Diagrama de Clases Sistema de Gestión de Biblioteca](#-Diagrama_de_Clases_Sistema_de_Gestión_de_Biblioteca)
- [Base de Datos](#-Base_de_Datos)
- [Diagrama entidad relacion Sistema de gestion de biblioteca](#--Diagrama_entidad_relacion_Sistema_de_gestion_de_biblioteca)
- [Instalación y Configuración Local](#-Instalación_y_Configuración_Local)
- [Casos de uso](#-Casos_de_uso)
- [Diagrama de casos de usos](#--Diagrama_de_casos_de_usos)
- [Endpoints](#-Endpoints)
- [Tecnologia Utilizada](#-Tecnologia_Utilizada)

  
## Descripción General
Este repositorios tiene el objetivo de crear una API REST con Spring Boot para gestionar las operaciones de una biblioteca. Esto incluye el registro de libros y autores, así como la gestión de préstamos de libros para los socios, controlando el estado de los préstamos (activo, devuelto) y la disponibilidad de los libros.

## Equipo de Desarollo
- Cristian Alexander Avalos Vásquez (AV23037)
- Luis Daniel Contreras Rivera (CR11019)
- José Fernando González León (GL24021)
- Mario Ernesto Montoya Vásquez (MV16013)
- Jose Emerson Alfaro Mendoza (AM22052)

## Objetivos
Implementar un sistema modular que permita:
- Registrar y administrar **Usuarios** (estados/tipos).
- Gestionar **Libros**, **Autores** y **Categorías**, con disponibilidad y stock.
- Administrar **Préstamos** y **Reservas** con sus estados y fechas clave.
- Generar y pagar **Multas** por retrasos.
  
## Arquitectura y Estructura
```
src/main/java/com/biblioteca/
├── controller/        # Capa de presentación (Maneja las peticiones HTTP)
├── dto/               # Objetos de Transferencia de Datos (DTOs)
├── entity/            # Modelos de la Base de Datos (JPA)
├── enums/             # Definiciones de tipos de estado (e.g., EstadoLibro, TipoUsuario)
├── exception/         # Manejadores de excepciones globales (GlobalExceptionHandler)
├── repository/        # Capa de acceso a datos (Spring Data JPA)
└── service/           # Capa de lógica de negocio (UsuarioService, PrestamoService)
```
### Diagrama de Clases Sistema de Gestión de Biblioteca
<img width="1957" height="881" alt="DiagramaClases" src="https://github.com/user-attachments/assets/e05443ab-0df6-431f-bbd8-52733a61836a" />

## Base de Datos
Entidades principales: Usuario, Libro, Autor, Categoria, Prestamo, Reserva, Multa.  
Estados/Enums: tipo_usuario, estado_usuario, estado_libro, estado_prestamo, estado_reserva, estado_multa.

### Diagrama entidad relacion Sistema de gestion de biblioteca
<img width="3840" height="3279" alt="DiagramaEntidadRelacion" src="https://github.com/user-attachments/assets/a24bd280-5162-42ef-bf02-a2d359324b1c" />


## Instalación y Configuración Local

### Requisitos Previos
- Java 17 o superior.
- PostgreSQL instalado y corriendo localmente.
- Spring Boot
- Maven.
### Paso 1: Clonar el Repositorio
- Ir a la ubicacion de la PC donde se desee copiar el repositorio del proyecto
- Con el uso de Git desk hace abrir **Git Bash**
- Ejecutar **git clone https://github.com/MV16013/API-para-un-Sistema-de-Biblioteca-.git**

### Paso 2: Configuración de la Base de Datos
- **Crear Base de Datos:**
  - Abrir PgAdmin
  - Haz la conexcion del servidor
  - Dar click derecho y seleccionar Crea una base de datos PostgreSQL llamada biblioteca_db (o el nombre que desees).
  <img width="585" height="205" alt="image" src="https://github.com/user-attachments/assets/a2354b0c-e8d9-40ea-845d-84efa0cfc739" />
  <img width="710" height="558" alt="image" src="https://github.com/user-attachments/assets/e61d6fb7-e0f6-4e2d-93ea-d9788ae3d1f3" />

- **Configurar Credenciales en el codigo Java:**
  
  - Copia el archivo "application-template.properties" y pegarlo en la misma ubicacion (deberas tener 2 archivos iguales).
  - Renombra la copia a "application.properties".
  <img width="283" height="77" alt="image" src="https://github.com/user-attachments/assets/87aa26e6-8b26-4b0b-a1ce-09389c0dff29" />
  
  - Edita "application.properties" para incluir tus credenciales locales de PostgreSQL:
     - Deberas colocar en **"usuario_local"** el usuario en postgreSQL; en PostgreSQL el usuario por defecto es **"postgres"**" 
     - Deberas colocar en **contraseña_local** La contraseña que usas para entrar y establecer conexcion en el servidor en PostgreSQL / 
  <img width="580" height="165" alt="image" src="https://github.com/user-attachments/assets/24ca96fd-3053-4580-889c-cfbfeda503a4" />
### Paso 3: Ejecutar la Aplicación
- Puedes ejecutar la aplicación directamente desde tu IDE (IntelliJ IDEA

### Notas:
- Esquema de Datos
    - Las tablas se crean automáticamente al iniciar la aplicación **(spring.jpa.hibernate.ddl-auto=update)**.
    - Los datos iniciales de prueba se insertan mediante el archivo **src/main/resources/data.sql**.

- Configuración del Driver
    - Se utiliza el driver **org.postgresql.Driver**, definido en el **pom.xml**, para asegurar la conectividad con la base de datos.

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
   
### Diagrama de casos de usos
![WhatsApp Image 2025-09-26 at 11 32 50 PM](https://github.com/user-attachments/assets/6e48cd32-59f3-46c5-9fcb-82e481c0bc2b)

## Endpoints
### **Usuarios** (`/api/usuarios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/usuarios` | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/usuarios` | Crear nuevo usuario |
| PUT | `/api/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario |
| PATCH | `/api/usuarios/{id}/suspender` | Suspender usuario |
| PATCH | `/api/usuarios/{id}/activar` | Activar usuario |

### **Libros** (`/api/libros`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/libros` | Listar todos los libros |
| GET | `/api/libros/{id}` | Obtener libro por ID |
| GET | `/api/libros/isbn/{isbn}` | Obtener libro por ISBN |
| GET | `/api/libros/disponibles` | Listar libros disponibles |
| GET | `/api/libros/buscar/titulo?titulo={titulo}` | Buscar por título |
| GET | `/api/libros/buscar/autor?autor={autor}` | Buscar por autor |
| POST | `/api/libros` | Crear nuevo libro |
| PUT | `/api/libros/{id}` | Actualizar libro |
| DELETE | `/api/libros/{id}` | Eliminar libro |

### **Préstamos** (`/api/prestamos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/prestamos` | Listar todos los préstamos |
| GET | `/api/prestamos/{id}` | Obtener préstamo por ID |
| GET | `/api/prestamos/usuario/{usuarioId}` | Préstamos de un usuario |
| GET | `/api/prestamos/activos` | Listar préstamos activos |
| GET | `/api/prestamos/vencidos` | Listar préstamos vencidos |
| POST | `/api/prestamos` | Crear nuevo préstamo |
| PATCH | `/api/prestamos/{id}/devolver` | Devolver libro |
| DELETE | `/api/prestamos/{id}` | Eliminar préstamo |

## Tecnologia Utilizada
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-FCC624?style=for-the-badge&logo=PostgreSQL&logoColor=black)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2496ED?style=for-the-badge&logo=SpringBoot&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-F05032?style=for-the-badge&logo=Postman&logoColor=white)
![Junit](https://img.shields.io/badge/Junit-F05032?style=for-the-badge&logo=Junit&logoColor=white)

### Cómo contribuir al repositorio:

```bash
# 1. Clonar el repositorio
git clone https://github.com/MV16013/API-para-un-Sistema-de-Biblioteca-.git

# 2. Crear una rama para tu feature
git checkout -b feature/nueva-funcionalidad

# 3. Hacer cambios y commit
git add .
git commit -m "Descripción de los cambios"

# 4. Push a tu rama
git push origin feature/nueva-funcionalidad

# 5. Crear Pull Request en GitHub
```
## Contacto y Soporte

Para preguntas o sugerencias sobre este proyecto:

- **Repositorio:** [API-para-un-Sistema-de-Biblioteca](https://github.com/MV16013/API-para-un-Sistema-de-Biblioteca-)
- **Issues:** [Reportar un problema](https://github.com/MV16013/API-para-un-Sistema-de-Biblioteca-/issues)
- **Universidad:** Universidad de El Salvador
- **Facultad:** Ingeniería y Arquitectura
- **Escuela:** Ingeniería en Desarrollo de Software

<div align="center">


**Desarrollado  por el Grupo 2**

**Universidad de El Salvador - 2025**

---

![Made with Java](https://img.shields.io/badge/Made%20with-Java-FCC624?style=for-the-badge&logo=Java&logoColor=black)
![Powered by PostgreSQL](https://img.shields.io/badge/Powered%20by-PostgreSQL-2496ED?style=for-the-badge&logo=PostgreSQL&logoColor=white)
![Built with Spring Boot](https://img.shields.io/badge/Built%20with-Spring_Boot-4EAA25?style=for-the-badge&logo=Spring_Booth&logoColor=white)

</div>
