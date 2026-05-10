-- init.sql
-- Script de inicialización para la base de datos LearningPlatform

-- Usar la base de datos creada por Docker Compose
USE app_db;

-- Crear tabla usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL
);

-- Crear tabla cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    precio INT NOT NULL,
    cupos INT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_academico BIGINT NOT NULL
);

-- Insertar datos de ejemplo en usuarios
INSERT INTO usuarios (nombre, correo, contrasena, rol, estado) VALUES
('Juan Pérez', 'juan@example.com', '$2a$10$encryptedpassword1', 'ESTUDIANTE', 'ACTIVO'),
('María García', 'maria@example.com', '$2a$10$encryptedpassword2', 'ACADEMICO', 'ACTIVO'),
('Carlos López', 'carlos@example.com', '$2a$10$encryptedpassword3', 'ESTUDIANTE', 'ACTIVO'),
('Ana Rodríguez', 'ana@example.com', '$2a$10$encryptedpassword4', 'ADMIN', 'ACTIVO');

-- Insertar datos de ejemplo en cursos
INSERT INTO cursos (nombre, descripcion, categoria, precio, cupos, estado, id_academico) VALUES
('Introducción a Java', 'Curso básico de programación en Java', 'Programación', 99, 50, 'ACTIVO', 2),
('Desarrollo Web con Spring', 'Aprende a crear aplicaciones web con Spring Boot', 'Desarrollo Web', 149, 30, 'ACTIVO', 4),
('Bases de Datos SQL', 'Fundamentos de SQL y diseño de bases de datos', 'Bases de Datos', 79, 40, 'ACTIVO', 2);
