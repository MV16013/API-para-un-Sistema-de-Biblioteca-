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
