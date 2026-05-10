package com.duoc.LearningPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idCurso;

    @Column(nullable = false)
    private Long idEstudiante;

    @Column(nullable = false)
    private String estado; // PENDIENTE_PAGO, ACTIVA, COMPLETADA, CANCELADA

    @Column(nullable = false)
    private LocalDateTime fechaInscripcion;
}