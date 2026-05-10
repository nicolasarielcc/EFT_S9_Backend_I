package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena; // encriptada con BCrypt

    @Column(nullable = false)
    private String rol; // ESTUDIANTE, ACADEMICO, ADMIN
    
    @Column(nullable = false)
    private String estado; // ACTIVO, BLOQUEADO, INACTIVO
}