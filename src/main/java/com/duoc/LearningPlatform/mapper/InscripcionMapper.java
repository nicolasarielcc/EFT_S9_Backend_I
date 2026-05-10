package com.duoc.LearningPlatform.mapper;

import com.duoc.LearningPlatform.dto.InscripcionRequestDTO;
import com.duoc.LearningPlatform.dto.InscripcionResponseDTO;
import com.duoc.LearningPlatform.model.Inscripcion;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InscripcionMapper {

    public Inscripcion toEntity(InscripcionRequestDTO dto) {
        return Inscripcion.builder()
                .idCurso(dto.getIdCurso())
                .idEstudiante(dto.getIdEstudiante())
                .estado("PENDIENTE_PAGO") // Estado inicial por defecto
                .fechaInscripcion(LocalDateTime.now())
                .build();
    }

    public InscripcionResponseDTO toResponse(Inscripcion entity) {
        return InscripcionResponseDTO.builder()
                .id(entity.getId())
                .idCurso(entity.getIdCurso())
                .idEstudiante(entity.getIdEstudiante())
                .estado(entity.getEstado())
                .fechaInscripcion(entity.getFechaInscripcion())
                .build();
    }
}