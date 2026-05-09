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
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer cupos;

    @Column(nullable = false)
    private String estado; // ACTIVO, INACTIVO

    // Relación simple referencial (Microservicios-style)
    @Column(nullable = false)
    private Long idAcademico; 
}