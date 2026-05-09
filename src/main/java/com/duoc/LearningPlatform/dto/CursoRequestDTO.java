package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequestDTO {

    // Validaciones para el DTO de solicitud de curso
    @NotBlank(message = "El nombre del curso es obligatorio")
    private String nombre;

    // Validación para la descripción del curso
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    // Validación para la categoría del curso
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    // Validación para el precio del curso
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Integer precio;

    // Validación para los cupos del curso
    @NotNull(message = "Los cupos son obligatorios")
    @Min(value = 1, message = "Debe haber al menos 1 cupo")
    private Integer cupos;

    // Validación para el estado del curso
    @NotBlank(message = "El estado es obligatorio (ACTIVO, INACTIVO)")
    private String estado;

    // Validación para el ID del académico responsable del curso
    @NotNull(message = "El ID del académico responsable es obligatorio")
    private Long idAcademico;
}