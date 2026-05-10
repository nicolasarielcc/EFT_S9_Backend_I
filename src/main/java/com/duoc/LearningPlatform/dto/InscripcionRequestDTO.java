package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionRequestDTO {
    @NotNull(message = "El ID del curso es obligatorio")
    private Long idCurso;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;
}