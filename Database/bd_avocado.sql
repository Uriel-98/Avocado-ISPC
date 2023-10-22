CREATE DATABASE Avocado;
USE Avocado;

/*Tabla Usuarios*/

CREATE TABLE usuarios(
idUsuario INT PRIMARY KEY,
nombreCompleto VARCHAR(150) NOT NULL,
imagen BLOB,
usuario VARCHAR(15) UNIQUE,
email VARCHAR(200) UNIQUE NOT NULL,
contraseña CHAR(60) NOT NULL
);

CREATE TABLE recetas(
idReceta INT PRIMARY KEY,
titulo VARCHAR(250) NOT NULL,
creadoPor INT NOT NULL,
tiempoCoccion VARCHAR(20),
dificultad VARCHAR(12),
imagen BLOB,
fechaCreacion DATETIME NOT NULL,
fechaActualizacion DATETIME NOT NULL,
CONSTRAINT fk_creado FOREIGN KEY(creadoPor) REFERENCES usuarios(idUsuario)
);


CREATE TABLE categorias(
idCategoria INT PRIMARY KEY,
nombre VARCHAR(24) NOT NULL
);

CREATE TABLE ingredientes(
idIngrediente INT PRIMARY KEY AUTO_INCREMENT,
idReceta INT NOT NULL,
nombre VARCHAR(50) NOT NULL,
CONSTRAINT fk_receta FOREIGN KEY(idReceta) REFERENCES recetas(idReceta)
);

CREATE TABLE pasos(
idPaso INT PRIMARY KEY AUTO_INCREMENT,
idReceta INT NOT NULL,
titulo VARCHAR(20) NOT NULL,
descripcion TEXT NOT NULL,
CONSTRAINT fk_paso FOREIGN KEY(idReceta) REFERENCES recetas(idReceta)
);

CREATE TABLE favoritos(
idFavorito INT PRIMARY KEY AUTO_INCREMENT,
idUsuario INT NOT NULL,
idReceta INT NOT NULL,
CONSTRAINT fk_favUsuario FOREIGN KEY(idUsuario) REFERENCES usuarios(idUsuario),
CONSTRAINT fk_favReceta FOREIGN KEY(idReceta) REFERENCES recetas(idReceta)
);


CREATE TABLE recetas_categorias(
idRecetaCategoria INT PRIMARY KEY AUTO_INCREMENT,
idReceta INT NOT NULL,
idCategoria INT NOT NULL,
CONSTRAINT fk_catReceta FOREIGN KEY(idReceta) REFERENCES recetas(idReceta),
CONSTRAINT fk_catCategoria FOREIGN KEY(idCategoria) REFERENCES categorias(idCategoria)
);

INSERT INTO usuarios (idUsuario, nombreCompleto, usuario, email, contraseña)
VALUES
  (1, 'Juan Pérez', 'juanito123', 'juan@example.com', 'contraseña123'),
  (2, 'María Rodríguez', 'maria456', 'maria@example.com', 'clave456'),
  (3, 'Carlos González', 'carlos789', 'carlos@example.com', 'pwd789'),
  (4, 'Luisa Martínez', 'luisa101', 'luisa@example.com', 'secreto101'),
  (5, 'Ana Sánchez', 'ana2022', 'ana@example.com', 'clave2022'),
  (6, 'Pedro López', 'pedrito99', 'pedro@example.com', 'contraseña99'),
  (7, 'Laura Ramírez', 'laura888', 'laura@example.com', 'secreto888'),
  (8, 'Jorge Hernández', 'jorge777', 'jorge@example.com', 'pwd777'),
  (9, 'Marta Torres', 'marta555', 'marta@example.com', 'clave555'),
  (10, 'Santiago Rodríguez', 'santiago333', 'santiago@example.com', 'contraseña333');
  
INSERT INTO recetas (idReceta, titulo, creadoPor, tiempoCoccion, dificultad, fechaCreacion, fechaActualizacion)
VALUES
  (1, 'Sanguche de jamón y queso', 1, '30 minutos', 'Fácil', NOW(), NOW()),
  (2, 'Yogur con cereal', 3, '45 minutos', 'Medio', NOW(), NOW()),
  (3, 'Pan con mermelada', 5, '60 minutos', 'Difícil', NOW(), NOW());
  
  INSERT INTO pasos (idReceta, titulo, descripcion)
  VALUES
  (1, 'Cortar el pan', 'Agarrá el pan, agarrá el cuchillo y cortá el pan por la mitad. Agregale mayonesa si querés. Opcional: Aderezos.'),
  (1, 'Preparar el relleno', 'Abrí el paquete con jamón (porque seguro lo compraste en el super) y ponele dos fetas al pan. No me seas rata. Hacé lo mismo con el queso.'),
  (1, 'Degustar', 'Cerrá los panes, aplastá el pan, abrí la boca y mandale un bocado.');

INSERT INTO ingredientes (idReceta, nombre)
VALUES
(1, '250g de jamón cocido'),
(1, '100g de queso feta'),
(1, 'un bollo de pan'),
(1, 'aderezo a gusto');

SELECT r.titulo, i.idIngrediente, i.nombre FROM recetas r
INNER JOIN ingredientes i 
ON r.idReceta = i.idReceta
WHERE r.idReceta = 1;
  
/*
- crear consulta que me traiga una lista de las recetas con sus categorías, pasos e ingredientes dado el id del usuario. (las recetas que  creó él)
- Procedimiento almacenado para cuando modifique una receta, tengo que modificar receta, categoria, pasos e ingredientes
- Crear trigger que se active cuando elimine una receta. Eliminar los pasos, las categorías y los ingredientes. 
*/
  
/*Procedimientos almacenados*/

-- Registro

-- Iniciar sesión

DELIMITER //
CREATE PROCEDURE `sp_iniciarSesion`(IN userEmail VARCHAR(200))
BEGIN
DECLARE mailBD VARCHAR(200);
SET mailBD = (SELECT email FROM usuarios WHERE email = userEmail);
IF mailBD IS NOT NULL
 THEN SELECT true AS success, (SELECT contraseña FROM usuarios WHERE email = mailBD) AS result;
 ELSE SELECT false AS success, '' AS result;
END IF;
END
//

DELIMITER //
CREATE PROCEDURE `sp_registro`(IN userFullName VARCHAR(150), IN userEmail VARCHAR(200), IN pass CHAR(60))
BEGIN
DECLARE mailBD VARCHAR(200);
SET mailBD = (SELECT email FROM usuarios WHERE email = userEmail);
IF mailBD IS NULL 
THEN 
	INSERT INTO usuarios (nombreCompleto, imagen, usuario, email, contraseña)
    VALUES(userFullName, NULL, NULL, userEmail, pass);
    SELECT true AS success, 'Usuario registrado' AS message;
ELSE SELECT false AS success, 'Ya existe un usuario con este email' AS message;
END IF;
END
//

CALL sp_registro('Juampi Lopez','lalari@example.com', '$2b$12$sl4R1G4fR9sj527A4hqUWe2dpY/DmHiQ/pHrxPpTiQ1Ub7j7m2v8e');

SELECT * FROM usuarios WHERE email = 'tuvieja@example.com';
