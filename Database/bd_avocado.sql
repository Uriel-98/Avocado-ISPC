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

ALTER TABLE recetas_categorias DROP CONSTRAINT fk_catReceta;
ALTER TABLE favoritos DROP CONSTRAINT fk_favUsuario;
ALTER TABLE recetas DROP CONSTRAINT fk_receta;

ALTER TABLE usuarios MODIFY idUsuario INT AUTO_INCREMENT;

ALTER TABLE recetas_categorias ADD CONSTRAINT fk_catReceta FOREIGN KEY(idReceta) REFERENCES recetas(idReceta);
ALTER TABLE favoritos ADD CONSTRAINT fk_favUsuario FOREIGN KEY(idUsuario) REFERENCES usuarios(idUsuario);
ALTER TABLE recetas ADD CONSTRAINT fk_creado FOREIGN KEY(creadoPor) REFERENCES usuarios(idUsuario);

CREATE TABLE recetas_categorias(
idRecetaCategoria INT PRIMARY KEY AUTO_INCREMENT,
idReceta INT NOT NULL,
idCategoria INT NOT NULL,
CONSTRAINT fk_catReceta FOREIGN KEY(idReceta) REFERENCES recetas(idReceta),
CONSTRAINT fk_catCategoria FOREIGN KEY(idCategoria) REFERENCES categorias(idCategoria)
);

-- INSERTAR CATEGORIAS PARA LA TABLA CATEGORIAS
INSERT INTO categorias (idCategoria, nombre) VALUES (1, 'Desayuno'), (2, 'Almuerzo'), (3, 'Cena'), (4, 'Entradas'), (5, 'Aperitivos'), (6, 'Snacks'), 
(7, 'Ensaladas'), (8, 'Platos principales'), (9, 'Guarniciones'), (10, 'Sopas y caldos'), (11, 'Postres'), (12, 'Panadería'), (13, 'Batidos y smoothies'), (14, 'Comida saludable'),
(15, 'Vegetariano/vegano'), (16, 'Comida sin gluten'), (17, 'Tradicional'), (18, 'Internacionales'), (19, 'Dulces'), (20, 'Eventos');

-- DATOS DE PRUEBA 
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


  
/*------ PROCEDIMIENTOS ALMACENADOS ------*/

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

-- Registro
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

-- Actualizar datos de perfil
DELIMITER //
CREATE PROCEDURE `sp_actualizarPerfil` (IN userEmail VARCHAR(200), IN userFullName VARCHAR(150), IN userImg BLOB, IN userName VARCHAR(15))
BEGIN 
	DECLARE consulta VARCHAR(500);
	SET consulta = 'UPDATE usuarios SET ';
		IF userFullName IS NOT NULL
			THEN SET consulta = CONCAT(consulta, 'nombreCompleto = "', userFullName, '", ');
		END IF;
		IF userImg IS NOT NULL
			THEN SET consulta = CONCAT(consulta, 'imagen = "', userImg, '", ');
		END IF;
		IF userName IS NOT NULL
			THEN SET consulta = CONCAT(consulta, 'usuario = "', userName, '", ');
		END IF;
	SET consulta = SUBSTRING(consulta, 1, LENGTH(consulta) - 2);
	SET consulta = CONCAT(consulta, ' WHERE email = "', userEmail, '";');
	SET @stmt = consulta;
	PREPARE stmt FROM @stmt;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 
END
//

-- Traer toda la info de una receta (vista detallada)
DELIMITER //
CREATE PROCEDURE `sp_getReceta`(IN idRequest INT)
BEGIN
	IF EXISTS(SELECT * FROM recetas WHERE idReceta = idRequest)
		THEN
			WITH categorias AS (
			  SELECT JSON_ARRAYAGG(nombre) AS categorias
			  FROM categorias c
			  INNER JOIN recetas_categorias rc
			  ON c.idCategoria = rc.idCategoria
			  WHERE rc.idReceta = idRequest
			),
			pasos AS (
			  SELECT JSON_ARRAYAGG(JSON_OBJECT('titulo', p.titulo, 'descripcion', p.descripcion)) AS pasos
			  FROM pasos p
			  INNER JOIN recetas r
			  ON r.idReceta = p.idReceta
			  WHERE r.idReceta = idRequest
			),
			ingredientes AS (
			SELECT JSON_ARRAYAGG(i.nombre) AS ingredientes FROM ingredientes i 
			INNER JOIN recetas r
			ON i.idReceta = r.idReceta
			WHERE r.idReceta = idRequest
			),
			recetas AS (
			SELECT * 
            FROM recetas 
            WHERE idReceta = idRequest
			)
			SELECT * 
            FROM recetas, ingredientes, pasos, categorias;
		ELSE
			SELECT 'No hay registros' AS result;
	END IF;
END
//

-- Traer recetas del usuario
DELIMITER //
CREATE PROCEDURE `sp_getRecetasUsuario` (IN emailUsuario VARCHAR(200))
BEGIN
	DECLARE id INT;
	SET id = (SELECT idUsuario FROM usuarios WHERE email = emailUsuario);
	IF (SELECT COUNT(*) FROM recetas WHERE creadoPor = id) > 0
		THEN
			SELECT idReceta, titulo, imagen FROM recetas WHERE creadoPor = id;
		ELSE SELECT "No tienes recetas" AS result;
	END IF;
END
//







/*------ TRIGGERS ------*/

DELIMITER //
CREATE TRIGGER onUserDelete BEFORE DELETE ON usuarios
FOR EACH ROW 
DELETE FROM recetas WHERE creadoPor = OLD.idUsuario
//

DELIMITER //
CREATE TRIGGER onRecipeDeleteCategories BEFORE DELETE ON recetas
FOR EACH ROW
DELETE FROM recetas_categorias WHERE idReceta = OLD.idReceta;
//

DELIMITER //
CREATE TRIGGER onRecipeDeleteSteps BEFORE DELETE ON recetas
FOR EACH ROW
DELETE FROM pasos WHERE idReceta = OLD.idReceta;
//

