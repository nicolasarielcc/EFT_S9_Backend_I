package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TareaRequestDTO {
    @NotNull(message = "ID del curso es obligatorio")
    private Long idCurso;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "La fecha de entrega es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    private LocalDate fechaEntrega;
}