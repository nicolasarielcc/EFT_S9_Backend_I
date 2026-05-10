package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.InscripcionCreadaEvent;
import com.duoc.LearningPlatform.dto.InscripcionRequestDTO;
import com.duoc.LearningPlatform.dto.InscripcionResponseDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.mapper.InscripcionMapper;
import com.duoc.LearningPlatform.model.Inscripcion;
import com.duoc.LearningPlatform.repository.InscripcionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository repository;

    @Autowired
    private InscripcionMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public InscripcionResponseDTO crear(InscripcionRequestDTO request) {
        log.info("Procesando inscripción de estudiante {} en curso {}", request.getIdEstudiante(), request.getIdCurso());
        
        Inscripcion inscripcion = mapper.toEntity(request);
        Inscripcion guardada = repository.save(inscripcion);
        
        log.info("Inscripción creada exitosamente con estado PENDIENTE_PAGO. ID: {}", guardada.getId());
        
        // Simulación Asincrónica (Kafka/RabbitMQ) - Publicar evento
        log.info("Publicando evento 'InscripcionCreadaEvent'...");
        eventPublisher.publishEvent(new InscripcionCreadaEvent(this, guardada.getId(), guardada.getIdEstudiante(), guardada.getIdCurso()));
        
        return mapper.toResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<InscripcionResponseDTO> listarPorEstudiante(Long idEstudiante) {
        return repository.findByIdEstudiante(idEstudiante).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InscripcionResponseDTO> listarPorCurso(Long idCurso) {
        return repository.findByIdCurso(idCurso).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public InscripcionResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Inscripcion inscripcion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + id));
        
        inscripcion.setEstado(nuevoEstado);
        log.info("Estado de la inscripción {} actualizado a {}", id, nuevoEstado);
        return mapper.toResponse(repository.save(inscripcion));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Inscripción no encontrada con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Inscripción {} eliminada", id);
    }
}