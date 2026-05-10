package com.duoc.LearningPlatform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagoResponseDTO {
    private Long id;
    private Long idInscripcion;
    private Integer monto;
    private String metodoPago;
    private String estado;
    private String referenciaBanco;
}