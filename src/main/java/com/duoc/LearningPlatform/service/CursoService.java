package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.CursoRequestDTO;
import com.duoc.LearningPlatform.dto.CursoResponseDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.mapper.CursoMapper;
import com.duoc.LearningPlatform.model.Curso;
import com.duoc.LearningPlatform.repository.CursoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private CursoMapper mapper;

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> obtenerTodos() {
        log.info("Consultando todos los cursos disponibles");
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO obtenerPorId(Long id) {
        log.info("Consultando curso con ID: {}", id);
        Curso curso = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Curso con ID {} no encontrado en la base de datos", id);
                    return new ResourceNotFoundException(
                        String.format("Curso no encontrado. El ID '%d' no corresponde a ningún curso registrado en la plataforma.", id)
                    );
                });
        log.info("Curso con ID {} consultado exitosamente - Nombre: {} - Categoría: {}", id, curso.getNombre(), curso.getCategoria());
        return mapper.toResponse(curso);
    }

    @Transactional
    public CursoResponseDTO crear(CursoRequestDTO request) {
        log.info("Creando nuevo curso: {} - Categoría: {} - Precio: ${}", request.getNombre(), request.getCategoria(), request.getPrecio());
        Curso guardado = repository.save(mapper.toEntity(request));
        log.info("Curso creado exitosamente con ID: {} - Cupos disponibles: {} - Creador: ID {}", guardado.getId(), guardado.getCupos(), guardado.getIdAcademico());
        return mapper.toResponse(guardado);
    }

    @Transactional
    public CursoResponseDTO actualizar(Long id, CursoRequestDTO request) {
        log.info("Actualizando curso con ID: {} - Nuevo nombre: {}", id, request.getNombre());
        Curso existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    String.format("No se puede actualizar. El curso con ID '%d' no existe en el sistema.", id)
                ));

        existente.setNombre(request.getNombre());
        existente.setDescripcion(request.getDescripcion());
        existente.setCategoria(request.getCategoria());
        existente.setPrecio(request.getPrecio());
        existente.setCupos(request.getCupos());
        existente.setEstado(request.getEstado());
        existente.setIdAcademico(request.getIdAcademico());

        Curso actualizado = repository.save(existente);
        log.info("Curso ID {} actualizado exitosamente - Nuevo estado: {}", id, actualizado.getEstado());
        return mapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Intentando eliminar curso con ID: {}", id);
        if (!repository.existsById(id)) {
            log.error("No se puede eliminar. Curso con ID {} no existe", id);
            throw new ResourceNotFoundException(
                String.format("No se puede eliminar. El curso con ID '%d' no existe en el sistema.", id)
            );
        }
        repository.deleteById(id);
        log.info("Curso con ID {} eliminado correctamente de la plataforma", id);
    }
}