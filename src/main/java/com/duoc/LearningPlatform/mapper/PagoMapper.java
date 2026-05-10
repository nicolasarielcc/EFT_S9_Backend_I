package com.duoc.LearningPlatform.mapper;

import com.duoc.LearningPlatform.dto.PagoRequestDTO;
import com.duoc.LearningPlatform.dto.PagoResponseDTO;
import com.duoc.LearningPlatform.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    public Pago toEntity(PagoRequestDTO dto) {
        return Pago.builder()
                .idInscripcion(dto.getIdInscripcion())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .estado("PENDIENTE_CONFIRMACION") // Estado inicial sincrónico
                .build();
    }

    public PagoResponseDTO toResponse(Pago entity) {
        return PagoResponseDTO.builder()
                .id(entity.getId())
                .idInscripcion(entity.getIdInscripcion())
                .monto(entity.getMonto())
                .metodoPago(entity.getMetodoPago())
                .estado(entity.getEstado())
                .referenciaBanco(entity.getReferenciaBanco())
                .build();
    }
}