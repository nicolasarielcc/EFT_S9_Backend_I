package com.duoc.LearningPlatform.mapper;

import com.duoc.LearningPlatform.dto.UsuarioRequestDTO;
import com.duoc.LearningPlatform.dto.UsuarioResponseDTO;
import com.duoc.LearningPlatform.model.Usuario;
import org.springframework.stereotype.Component;

// Mapper para convertir entre UsuarioRequestDTO, UsuarioResponseDTO y la entidad Usuario
@Component
public class UsuarioMapper {
    
    // Convierte un UsuarioRequestDTO a una entidad Usuario
    public Usuario toEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .contrasena(dto.getContrasena()) // Contraseña encriptada en la capa Service
                .rol(dto.getRol())
                .estado(dto.getEstado())
                .build();
    }

    // Convierte una entidad Usuario a un UsuarioResponseDTO
    public UsuarioResponseDTO toResponse(Usuario entity) {
        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .correo(entity.getCorreo())
                .rol(entity.getRol())
                .estado(entity.getEstado())
                // La contraseña no se incluye en el builder del response
                .build();
    }
}