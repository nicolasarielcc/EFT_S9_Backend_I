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
        Curso curso = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return mapper.toResponse(curso);
    }

    @Transactional
    public CursoResponseDTO crear(CursoRequestDTO request) {
        log.info("Creando nuevo curso: {}", request.getNombre());
        Curso guardado = repository.save(mapper.toEntity(request));
        log.info("Curso creado exitosamente con ID: {}", guardado.getId());
        return mapper.toResponse(guardado);
    }

    @Transactional
    public CursoResponseDTO actualizar(Long id, CursoRequestDTO request) {
        Curso existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));

        existente.setNombre(request.getNombre());
        existente.setDescripcion(request.getDescripcion());
        existente.setCategoria(request.getCategoria());
        existente.setPrecio(request.getPrecio());
        existente.setCupos(request.getCupos());
        existente.setEstado(request.getEstado());
        existente.setIdAcademico(request.getIdAcademico());

        return mapper.toResponse(repository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Curso con ID {} eliminado", id);
    }
}