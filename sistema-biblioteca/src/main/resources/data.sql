-- =====================================================
-- DATOS DE PRUEBA - SISTEMA DE BIBLIOTECA
-- =====================================================

-- =====================================================
-- USUARIOS
-- =====================================================
INSERT INTO usuarios (numero_identificacion, nombre, apellido, email, telefono, direccion, tipo_usuario, estado, fecha_registro)
VALUES
-- Usuarios Activos
('12345678', 'Juan', 'Pérez', 'juan.perez@email.com', '1234-5678', 'Calle Principal #123, San Salvador', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('87654321', 'María', 'González', 'maria.gonzalez@email.com', '8765-4321', 'Avenida Central #456, Santa Ana', 'PROFESOR', 'ACTIVO', CURRENT_TIMESTAMP),
('11223344', 'Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '1122-3344', 'Boulevard Sur #789, San Miguel', 'ADMINISTRATIVO', 'ACTIVO', CURRENT_TIMESTAMP),
('55667788', 'Laura', 'Hernández', 'laura.hernandez@email.com', '5566-7788', 'Calle Las Flores #321, Sonsonate', 'ESTUDIANTE', 'ACTIVO', CURRENT_TIMESTAMP),
('99001122', 'Roberto', 'López', 'roberto.lopez@email.com', '9900-1122', 'Avenida Norte #654, La Libertad', 'PROFESOR', 'ACTIVO', CURRENT_TIMESTAMP),

-- Usuarios Suspendidos/Inactivos
('99887766', 'Ana', 'Martínez', 'ana.martinez@email.com', '9988-7766', 'Calle Norte #321, Usulután', 'VISITANTE', 'SUSPENDIDO', CURRENT_TIMESTAMP),
('44556677', 'Pedro', 'Ramírez', 'pedro.ramirez@email.com', '4455-6677', 'Boulevard Este #159, Ahuachapán', 'ESTUDIANTE', 'INACTIVO', CURRENT_TIMESTAMP);

-- =====================================================
-- LIBROS
-- =====================================================
INSERT INTO libros (isbn, titulo, autor, editorial, descripcion, anio_publicacion, categoria, estado, stock_total, stock_disponible, fecha_registro)
VALUES
-- Literatura Latinoamericana
('978-0-307-47472-1', 'Cien Años de Soledad', 'Gabriel García Márquez', 'Vintage Español', 'La obra maestra del realismo mágico que narra la historia de la familia Buendía en el pueblo ficticio de Macondo.', 2006, 'Ficción', 'DISPONIBLE', 5, 5, CURRENT_TIMESTAMP),
('978-0-060-88328-3', 'Crónica de una Muerte Anunciada', 'Gabriel García Márquez', 'Harper Collins', 'Una investigación periodística sobre un asesinato anunciado en un pequeño pueblo costero.', 2003, 'Ficción', 'DISPONIBLE', 3, 3, CURRENT_TIMESTAMP),
('978-8-420-47175-7', 'La Casa de los Espíritus', 'Isabel Allende', 'Plaza & Janés', 'La saga familiar de los Trueba a través de varias generaciones en Chile.', 2015, 'Ficción', 'DISPONIBLE', 4, 4, CURRENT_TIMESTAMP),
('978-8-437-60454-1', 'Rayuela', 'Julio Cortázar', 'Cátedra', 'Una novela experimental que puede leerse de múltiples formas, considerada una de las grandes obras de la literatura hispanoamericana.', 2008, 'Ficción', 'DISPONIBLE', 2, 2, CURRENT_TIMESTAMP),

-- Clásicos Universales
('978-0-142-43723-0', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Penguin Classics', 'Las aventuras del ingenioso hidalgo Don Quijote y su fiel escudero Sancho Panza.', 2003, 'Clásicos', 'DISPONIBLE', 6, 6, CURRENT_TIMESTAMP),
('978-0-156-01222-8', 'El Principito', 'Antoine de Saint-Exupéry', 'Harcourt', 'Un cuento poético que trata sobre la amistad y el amor a través de las aventuras de un pequeño príncipe.', 2000, 'Infantil', 'DISPONIBLE', 8, 8, CURRENT_TIMESTAMP),

-- Ciencia Ficción y Distopías
('978-0-452-28423-4', '1984', 'George Orwell', 'Penguin Books', 'Una distopía sobre un régimen totalitario que controla todos los aspectos de la vida.', 1961, 'Ciencia Ficción', 'DISPONIBLE', 4, 4, CURRENT_TIMESTAMP),
('978-0-618-26030-1', 'Un Mundo Feliz', 'Aldous Huxley', 'Harper Perennial', 'Una sociedad futurista donde la felicidad se impone mediante el condicionamiento y las drogas.', 2006, 'Ciencia Ficción', 'DISPONIBLE', 3, 3, CURRENT_TIMESTAMP),
('978-1-451-67396-2', 'Fahrenheit 451', 'Ray Bradbury', 'Simon & Schuster', 'En un futuro donde los libros están prohibidos, un bombero cuya misión es quemarlos comienza a cuestionarse.', 2013, 'Ciencia Ficción', 'DISPONIBLE', 3, 3, CURRENT_TIMESTAMP),

-- Desarrollo Personal
('978-0-062-45747-3', 'El Alquimista', 'Paulo Coelho', 'HarperOne', 'La historia de un joven pastor andaluz que emprende un viaje en busca de su leyenda personal.', 2014, 'Autoayuda', 'DISPONIBLE', 5, 5, CURRENT_TIMESTAMP),
('978-1-501-13995-2', 'Los 7 Hábitos de la Gente Altamente Efectiva', 'Stephen Covey', 'Simon & Schuster', 'Un enfoque integral para resolver problemas personales y profesionales.', 2013, 'Autoayuda', 'DISPONIBLE', 4, 4, CURRENT_TIMESTAMP),

-- Literatura Juvenil
('978-0-439-02348-1', 'Harry Potter y la Piedra Filosofal', 'J.K. Rowling', 'Scholastic', 'El primer libro de la saga que narra las aventuras de un joven mago.', 1998, 'Fantasía', 'DISPONIBLE', 7, 7, CURRENT_TIMESTAMP),
('978-0-439-13959-5', 'El Señor de los Anillos: La Comunidad del Anillo', 'J.R.R. Tolkien', 'Houghton Mifflin', 'El inicio de la épica aventura en la Tierra Media.', 2001, 'Fantasía', 'DISPONIBLE', 5, 5, CURRENT_TIMESTAMP),

-- Libros con stock limitado (para probar disponibilidad)
('978-0-316-76948-0', 'El Guardián entre el Centeno', 'J.D. Salinger', 'Little Brown', 'La historia de Holden Caulfield, un adolescente en Nueva York tras ser expulsado del colegio.', 2001, 'Ficción', 'DISPONIBLE', 2, 2, CURRENT_TIMESTAMP),
('978-0-061-12008-4', 'Matar a un Ruiseñor', 'Harper Lee', 'Harper Perennial', 'Una novela sobre la injusticia racial en el sur de Estados Unidos durante los años 30.', 2006, 'Ficción', 'DISPONIBLE', 1, 1, CURRENT_TIMESTAMP);

-- =====================================================
-- PRÉSTAMOS
-- =====================================================
INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo, fecha_devolucion_prevista, estado, multa, observaciones)
VALUES
-- Préstamos Activos (sin vencer)
(1, 1, CURRENT_TIMESTAMP, DATEADD('DAY', 15, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Préstamo para trabajo de literatura latinoamericana'),
(2, 7, DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', 10, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Material para clase de filosofía política'),
(4, 10, DATEADD('DAY', -3, CURRENT_TIMESTAMP), DATEADD('DAY', 12, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Lectura de crecimiento personal'),
(5, 12, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', 29, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Material didáctico para curso de literatura fantástica'),

-- Préstamos Vencidos (pasó la fecha de devolución)
(1, 5, DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Préstamo vencido - Contactar al usuario'),
(4, 14, DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -5, CURRENT_TIMESTAMP), 'ACTIVO', 0.00, 'Retraso de 5 días'),

-- Préstamos Devueltos (completados)
(1, 6, DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), 'DEVUELTO', 0.00, 'Devuelto a tiempo | Devolución: Libro en perfecto estado'),
(2, 11, DATEADD('DAY', -45, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), 'DEVUELTO', 0.00, 'Devuelto a tiempo | Devolución: Sin novedades'),
(3, 8, DATEADD('DAY', -40, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), 'DEVUELTO', 0.00, 'Devuelto a tiempo | Devolución: Excelente estado'),
(5, 9, DATEADD('DAY', -35, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), 'DEVUELTO', 0.00, 'Devuelto a tiempo | Devolución: Libro usado para proyecto de investigación'),

-- Préstamos Devueltos con Retraso (con multa)
(4, 2, DATEADD('DAY', -50, CURRENT_TIMESTAMP), DATEADD('DAY', -35, CURRENT_TIMESTAMP), 'VENCIDO', 8.00, 'Devuelto con 8 días de retraso | Devolución: Multa aplicada'),
(1, 3, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', -45, CURRENT_TIMESTAMP), 'VENCIDO', 3.00, 'Devuelto con 3 días de retraso | Devolución: Pequeño retraso justificado');

-- =====================================================
-- ACTUALIZAR STOCK DE LIBROS PRESTADOS
-- =====================================================
-- Reducir stock de libros que están actualmente prestados
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 1;  -- Cien Años de Soledad (prestado a Juan)
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 7;  -- 1984 (prestado a María)
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 10; -- El Alquimista (prestado a Laura)
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 12; -- El Señor de los Anillos (prestado a Roberto)
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 5;  -- Don Quijote (prestado a Juan - vencido)
UPDATE libros SET stock_disponible = stock_disponible - 1 WHERE id = 14; -- Matar a un Ruiseñor (prestado a Laura - vencido)

-- Actualizar estado de libros sin stock
UPDATE libros SET estado = 'PRESTADO' WHERE id = 14 AND stock_disponible = 0;

-- =====================================================
-- RESUMEN DE DATOS INSERTADOS
-- =====================================================
-- 7 Usuarios (5 activos, 1 suspendido, 1 inactivo)
-- 14 Libros (diversos géneros y disponibilidad)
-- 12 Préstamos (4 activos, 2 vencidos, 6 devueltos)
-- =====================================================