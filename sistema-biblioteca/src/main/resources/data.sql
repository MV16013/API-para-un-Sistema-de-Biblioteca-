-- =====================================================
-- DATA.SQL - DATOS DE PRUEBA PARA SISTEMA BIBLIOTECA
-- =====================================================

-- INSERTAR AUTORES
INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento, biografia, fecha_registro) VALUES
('Gabriel', 'García Márquez', 'Colombiana', '1927-03-06', 'Escritor colombiano, ganador del Premio Nobel de Literatura en 1982. Autor de "Cien años de soledad".', CURRENT_TIMESTAMP),
('Isabel', 'Allende', 'Chilena', '1942-08-02', 'Escritora chilena, autora de bestsellers internacionales como "La casa de los espíritus".', CURRENT_TIMESTAMP),
('Jorge Luis', 'Borges', 'Argentina', '1899-08-24', 'Escritor argentino, uno de los autores más destacados de la literatura del siglo XX.', CURRENT_TIMESTAMP),
('Mario', 'Vargas Llosa', 'Peruana', '1936-03-28', 'Escritor peruano, Premio Nobel de Literatura 2010. Autor de "La ciudad y los perros".', CURRENT_TIMESTAMP),
('Julio', 'Cortázar', 'Argentina', '1914-08-26', 'Escritor argentino, maestro del cuento corto y la novela experimental.', CURRENT_TIMESTAMP),
('Pablo', 'Neruda', 'Chilena', '1904-07-12', 'Poeta chileno, Premio Nobel de Literatura 1971. Uno de los poetas más influyentes del siglo XX.', CURRENT_TIMESTAMP),
('Octavio', 'Paz', 'Mexicana', '1914-03-31', 'Poeta y ensayista mexicano, Premio Nobel de Literatura 1990.', CURRENT_TIMESTAMP),
('Carlos', 'Fuentes', 'Mexicana', '1928-11-11', 'Escritor mexicano, una de las figuras más importantes del boom latinoamericano.', CURRENT_TIMESTAMP),
('Laura', 'Esquivel', 'Mexicana', '1950-09-30', 'Escritora mexicana, autora de "Como agua para chocolate".', CURRENT_TIMESTAMP),
('Miguel de', 'Cervantes', 'Española', '1547-09-29', 'Escritor español, autor de "Don Quijote de la Mancha", considerada la primera novela moderna.', CURRENT_TIMESTAMP);

-- INSERTAR CATEGORÍAS
INSERT INTO categorias (codigo, nombre, descripcion, fecha_registro) VALUES
('FIC', 'Ficción', 'Novelas y cuentos de ficción literaria', CURRENT_TIMESTAMP),
('POE', 'Poesía', 'Colecciones de poesía y verso', CURRENT_TIMESTAMP),
('HIS', 'Historia', 'Libros de historia y biografías', CURRENT_TIMESTAMP),
('CIE', 'Ciencia', 'Libros científicos y de divulgación', CURRENT_TIMESTAMP),
('TEC', 'Tecnología', 'Libros sobre tecnología e informática', CURRENT_TIMESTAMP),
('FIL', 'Filosofía', 'Obras filosóficas y de pensamiento', CURRENT_TIMESTAMP),
('ART', 'Arte', 'Libros sobre arte, pintura y escultura', CURRENT_TIMESTAMP),
('INF', 'Infantil', 'Literatura infantil y juvenil', CURRENT_TIMESTAMP),
('ROM', 'Romance', 'Novelas románticas', CURRENT_TIMESTAMP),
('MIS', 'Misterio', 'Novelas de misterio y suspenso', CURRENT_TIMESTAMP);

-- INSERTAR LIBROS
INSERT INTO libros (isbn, titulo, editorial, descripcion, anio_publicacion, stock_total, stock_disponible, estado, fecha_registro) VALUES
('978-0-307-47472-4', 'Cien años de soledad', 'Editorial Sudamericana', 'La obra maestra de García Márquez sobre la familia Buendía en Macondo.', 1967, 10, 10, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-1-501-16774-3', 'La casa de los espíritus', 'Plaza & Janés', 'Saga familiar que recorre cuatro generaciones de la familia Trueba.', 1982, 8, 8, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-14-118726-6', 'Ficciones', 'Emecé', 'Colección de cuentos de Jorge Luis Borges que desafían la realidad.', 1944, 12, 12, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-374-52623-9', 'La ciudad y los perros', 'Alfaguara', 'Primera novela de Vargas Llosa sobre cadetes en un colegio militar.', 1963, 7, 7, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-394-75284-7', 'Rayuela', 'Editorial Sudamericana', 'Novela experimental de Cortázar que puede leerse de múltiples formas.', 1963, 9, 9, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-307-26349-8', 'Veinte poemas de amor y una canción desesperada', 'Nascimento', 'Poemario de amor de Pablo Neruda.', 1924, 15, 15, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-8021-5084-8', 'El laberinto de la soledad', 'Fondo de Cultura Económica', 'Ensayo de Octavio Paz sobre la identidad mexicana.', 1950, 6, 6, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-374-17632-0', 'La muerte de Artemio Cruz', 'Fondo de Cultura Económica', 'Novela de Carlos Fuentes sobre un revolucionario mexicano.', 1962, 5, 5, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-385-72123-4', 'Como agua para chocolate', 'Planeta', 'Novela de realismo mágico sobre amor y cocina en México.', 1989, 10, 10, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-06-093434-1', 'Don Quijote de la Mancha', 'Francisco de Robles', 'La obra cumbre de Cervantes sobre el ingenioso hidalgo.', 1605, 20, 20, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-307-38991-9', 'El amor en los tiempos del cólera', 'Editorial Sudamericana', 'Historia de amor entre Florentino Ariza y Fermina Daza.', 1985, 8, 8, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-06-088328-7', 'Crónica de una muerte anunciada', 'Editorial Sudamericana', 'Novela corta sobre un asesinato anunciado en un pueblo.', 1981, 11, 11, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-14-243723-0', 'El Aleph', 'Emecé', 'Colección de cuentos fantásticos de Borges.', 1949, 10, 10, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-06-093298-9', 'Conversación en la Catedral', 'Seix Barral', 'Novela de Vargas Llosa sobre el Perú bajo dictadura.', 1969, 6, 6, 'DISPONIBLE', CURRENT_TIMESTAMP),
('978-0-375-72721-5', 'Bestiario', 'Editorial Sudamericana', 'Primera colección de cuentos de Julio Cortázar.', 1951, 7, 7, 'DISPONIBLE', CURRENT_TIMESTAMP);

-- RELACIONAR LIBROS CON AUTORES (libro_autor)
INSERT INTO libro_autor (libro_id, autor_id) VALUES
(1, 1),   -- Cien años de soledad - García Márquez
(2, 2),   -- La casa de los espíritus - Isabel Allende
(3, 3),   -- Ficciones - Borges
(4, 4),   -- La ciudad y los perros - Vargas Llosa
(5, 5),   -- Rayuela - Cortázar
(6, 6),   -- Veinte poemas - Neruda
(7, 7),   -- El laberinto de la soledad - Octavio Paz
(8, 8),   -- La muerte de Artemio Cruz - Carlos Fuentes
(9, 9),   -- Como agua para chocolate - Laura Esquivel
(10, 10), -- Don Quijote - Cervantes
(11, 1),  -- El amor en los tiempos del cólera - García Márquez
(12, 1),  -- Crónica de una muerte anunciada - García Márquez
(13, 3),  -- El Aleph - Borges
(14, 4),  -- Conversación en la Catedral - Vargas Llosa
(15, 5);  -- Bestiario - Cortázar

-- RELACIONAR LIBROS CON CATEGORÍAS (libro_categoria)
INSERT INTO libro_categoria (libro_id, categoria_id) VALUES
(1, 1),   -- Cien años de soledad - Ficción
(2, 1),   -- La casa de los espíritus - Ficción
(3, 1),   -- Ficciones - Ficción
(4, 1),   -- La ciudad y los perros - Ficción
(5, 1),   -- Rayuela - Ficción
(6, 2),   -- Veinte poemas - Poesía
(7, 6),   -- El laberinto de la soledad - Filosofía
(8, 1),   -- La muerte de Artemio Cruz - Ficción
(8, 3),   -- La muerte de Artemio Cruz - Historia
(9, 1),   -- Como agua para chocolate - Ficción
(9, 9),   -- Como agua para chocolate - Romance
(10, 1),  -- Don Quijote - Ficción
(10, 3),  -- Don Quijote - Historia
(11, 1),  -- El amor en los tiempos del cólera - Ficción
(11, 9),  -- El amor en los tiempos del cólera - Romance
(12, 1),  -- Crónica de una muerte anunciada - Ficción
(12, 10), -- Crónica de una muerte anunciada - Misterio
(13, 1),  -- El Aleph - Ficción
(14, 1),  -- Conversación en la Catedral - Ficción
(14, 3),  -- Conversación en la Catedral - Historia
(15, 1);  -- Bestiario - Ficción

-- INSERTAR USUARIOS
INSERT INTO usuarios (numero_identificacion, nombre, apellido, email, telefono, direccion, tipo_usuario, estado, fecha_registro) VALUES
('1234567890', 'Juan', 'Pérez', 'juan.perez@email.com', '555-0101', 'Calle Principal 123', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('0987654321', 'María', 'González', 'maria.gonzalez@email.com', '555-0102', 'Avenida Central 456', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('1122334455', 'Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '555-0103', 'Boulevard Norte 789', 'PROFESOR', 'ACTIVO', CURRENT_TIMESTAMP),
('5544332211', 'Ana', 'Martínez', 'ana.martinez@email.com', '555-0104', 'Paseo del Sur 321', 'PROFESOR', 'ACTIVO', CURRENT_TIMESTAMP),
('6677889900', 'Luis', 'Hernández', 'luis.hernandez@email.com', '555-0105', 'Camino Real 654', 'ADMINISTRATIVO', 'ACTIVO', CURRENT_TIMESTAMP),
('9988776655', 'Laura', 'López', 'laura.lopez@email.com', '555-0106', 'Plaza Mayor 147', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('3344556677', 'Pedro', 'Sánchez', 'pedro.sanchez@email.com', '555-0107', 'Callejón Oscuro 258', 'ESTUDIANTE', 'SUSPENDIDO', CURRENT_TIMESTAMP),
('7788990011', 'Elena', 'García', 'elena.garcia@email.com', '555-0108', 'Ronda del Este 369', 'VISITANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('2233445566', 'Miguel', 'Torres', 'miguel.torres@email.com', '555-0109', 'Travesía Oeste 741', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('4455667788', 'Carmen', 'Ramírez', 'carmen.ramirez@email.com', '555-0110', 'Pasaje Centro 852', 'PROFESOR', 'ACTIVO', CURRENT_TIMESTAMP);

-- INSERTAR PRÉSTAMOS (algunos activos, algunos devueltos, algunos vencidos)
INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo, fecha_devolucion_prevista, fecha_devolucion_real, estado, observaciones, multa) VALUES
-- Préstamos activos
(1, 1, CURRENT_TIMESTAMP - INTERVAL '5' DAY, CURRENT_TIMESTAMP + INTERVAL '9' DAY, NULL, 'ACTIVO', 'Préstamo en curso', 0.00),
(2, 3, CURRENT_TIMESTAMP - INTERVAL '3' DAY, CURRENT_TIMESTAMP + INTERVAL '11' DAY, NULL, 'ACTIVO', 'Préstamo en curso', 0.00),
(3, 5, CURRENT_TIMESTAMP - INTERVAL '7' DAY, CURRENT_TIMESTAMP + INTERVAL '7' DAY, NULL, 'ACTIVO', 'Préstamo en curso', 0.00),

-- Préstamos devueltos
(4, 2, CURRENT_TIMESTAMP - INTERVAL '20' DAY, CURRENT_TIMESTAMP - INTERVAL '6' DAY, CURRENT_TIMESTAMP - INTERVAL '7' DAY, 'DEVUELTO', 'Devuelto a tiempo', 0.00),
(5, 4, CURRENT_TIMESTAMP - INTERVAL '25' DAY, CURRENT_TIMESTAMP - INTERVAL '11' DAY, CURRENT_TIMESTAMP - INTERVAL '10' DAY, 'DEVUELTO', 'Devuelto a tiempo', 0.00),
(6, 6, CURRENT_TIMESTAMP - INTERVAL '30' DAY, CURRENT_TIMESTAMP - INTERVAL '16' DAY, CURRENT_TIMESTAMP - INTERVAL '14' DAY, 'DEVUELTO', 'Devuelto con 2 días de retraso', 2.00),

-- Préstamos vencidos (sin devolver)
(7, 7, CURRENT_TIMESTAMP - INTERVAL '25' DAY, CURRENT_TIMESTAMP - INTERVAL '11' DAY, NULL, 'ACTIVO', 'Préstamo vencido hace 11 días', 11.00),
(9, 9, CURRENT_TIMESTAMP - INTERVAL '20' DAY, CURRENT_TIMESTAMP - INTERVAL '6' DAY, NULL, 'ACTIVO', 'Préstamo vencido hace 6 días', 6.00),

-- Préstamos renovados
(10, 10, CURRENT_TIMESTAMP - INTERVAL '10' DAY, CURRENT_TIMESTAMP + INTERVAL '11' DAY, NULL, 'RENOVADO', 'Préstamo renovado una vez', 0.00);

-- INSERTAR RESERVAS
INSERT INTO reservas (usuario_id, libro_id, fecha_reserva, fecha_vencimiento, estado, observaciones) VALUES
-- Reservas activas
(1, 8, CURRENT_TIMESTAMP - INTERVAL '1' DAY, CURRENT_TIMESTAMP + INTERVAL '2' DAY, 'ACTIVA', 'Esperando disponibilidad del libro'),
(2, 11, CURRENT_TIMESTAMP - INTERVAL '2' DAY, CURRENT_TIMESTAMP + INTERVAL '1' DAY, 'ACTIVA', 'Primera en lista de espera'),

-- Reservas vencidas
(6, 12, CURRENT_TIMESTAMP - INTERVAL '5' DAY, CURRENT_TIMESTAMP - INTERVAL '2' DAY, 'VENCIDA', 'No recogió el libro a tiempo'),

-- Reservas canceladas
(9, 13, CURRENT_TIMESTAMP - INTERVAL '3' DAY, CURRENT_TIMESTAMP + INTERVAL '0' DAY, 'CANCELADA', 'Usuario canceló la reserva');

-- INSERTAR MULTAS
INSERT INTO multas (usuario_id, prestamo_id, monto, concepto, fecha_generacion, fecha_pago, estado) VALUES
-- Multas pendientes
(7, 7, 11.00, 'Multa por retraso de 11 días en devolución de: El laberinto de la soledad', CURRENT_TIMESTAMP - INTERVAL '11' DAY, NULL, 'PENDIENTE'),
(9, 8, 6.00, 'Multa por retraso de 6 días en devolución de: Como agua para chocolate', CURRENT_TIMESTAMP - INTERVAL '6' DAY, NULL, 'PENDIENTE'),

-- Multas pagadas
(6, 6, 2.00, 'Multa por retraso de 2 días en devolución de: Veinte poemas de amor y una canción desesperada', CURRENT_TIMESTAMP - INTERVAL '16' DAY, CURRENT_TIMESTAMP - INTERVAL '14' DAY, 'PAGADA'),

-- Multas canceladas
(2, NULL, 5.00, 'Multa cancelada por error administrativo', CURRENT_TIMESTAMP - INTERVAL '10' DAY, NULL, 'CANCELADA');

-- Actualizar stock disponible de libros prestados
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id IN (1, 3, 5, 7, 9, 10);
UPDATE libros SET estado = 'PRESTADO' WHERE id IN (1, 3, 5, 7, 9, 10);