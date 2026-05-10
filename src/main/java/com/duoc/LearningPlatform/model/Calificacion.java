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
@Table(name = "calificaciones")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idEvaluacion;

    @Column(nullable = false)
    private Long idEstudiante;

    @Column(nullable = false)
    private Double nota;

    private String feedback;
}