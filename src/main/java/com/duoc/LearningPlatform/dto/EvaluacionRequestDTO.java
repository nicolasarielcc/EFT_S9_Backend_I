package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EvaluacionRequestDTO {
    @NotNull(message = "El ID del curso es obligatorio")
    private Long idCurso;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La ponderación es obligatoria")
    @Min(value = 1, message = "La ponderación mínima es 1%")
    @Max(value = 100, message = "La ponderación máxima es 100%")
    private Integer ponderacion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
}