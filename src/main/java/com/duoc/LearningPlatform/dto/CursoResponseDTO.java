package com.duoc.LearningPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// DTO para la respuesta de un curso
public class CursoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer precio;
    private Integer cupos;
    private String estado;
    private Long idAcademico;
}