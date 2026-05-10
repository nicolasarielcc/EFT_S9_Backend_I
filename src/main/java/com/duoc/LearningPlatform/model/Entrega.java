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
@Table(name = "entregas")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idTarea;

    @Column(nullable = false)
    private Long idEstudiante;

    @Lob // Para soportar strings largos (ej. base64)
    @Column(nullable = false)
    private String archivo;

    private String comentario;

    // Estados: ENTREGADA, PROCESANDO, COMPLETADA, RECHAZADA [cite: 520, 521, 522, 523]
    @Column(nullable = false)
    private String estado; 

    private Double plagio;
    private String antivirus;
}