package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WebhookPagoDTO {
    @NotNull(message = "El ID del pago es obligatorio")
    private Long idPago;

    @NotBlank(message = "El estado es obligatorio (APROBADO/RECHAZADO)")
    private String estado;

    @NotBlank(message = "La referencia del banco es obligatoria")
    private String referenciaBanco;
}