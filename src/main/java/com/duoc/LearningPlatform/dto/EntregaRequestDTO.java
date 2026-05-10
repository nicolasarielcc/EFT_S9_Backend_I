package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntregaRequestDTO {
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;

    @NotBlank(message = "El archivo (base64) no puede estar vacío")
    private String archivo;

    private String comentario;
}