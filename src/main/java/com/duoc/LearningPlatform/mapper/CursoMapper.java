package com.duoc.LearningPlatform.mapper;

import com.duoc.LearningPlatform.dto.CursoRequestDTO;
import com.duoc.LearningPlatform.dto.CursoResponseDTO;
import com.duoc.LearningPlatform.model.Curso;
import org.springframework.stereotype.Component;

// Mapper para convertir entre CursoRequestDTO, CursoResponseDTO y la entidad Curso
@Component
public class CursoMapper {

    // Convierte un CursoRequestDTO a una entidad Curso
    public Curso toEntity(CursoRequestDTO dto) {
        return Curso.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .precio(dto.getPrecio())
                .cupos(dto.getCupos())
                .estado(dto.getEstado())
                .idAcademico(dto.getIdAcademico())
                .build();
    }

    // Convierte una entidad Curso a un CursoResponseDTO
    public CursoResponseDTO toResponse(Curso entity) {
        return CursoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .categoria(entity.getCategoria())
                .precio(entity.getPrecio())
                .cupos(entity.getCupos())
                .estado(entity.getEstado())
                .idAcademico(entity.getIdAcademico())
                .build();
    }
}