package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.CalificacionRequestDTO;
import com.duoc.LearningPlatform.dto.EvaluacionCalificadaEvent;
import com.duoc.LearningPlatform.dto.EvaluacionRequestDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Calificacion;
import com.duoc.LearningPlatform.model.Evaluacion;
import com.duoc.LearningPlatform.repository.CalificacionRepository;
import com.duoc.LearningPlatform.repository.EvaluacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public Evaluacion crearEvaluacion(EvaluacionRequestDTO dto) {
        Evaluacion evaluacion = Evaluacion.builder()
                .idCurso(dto.getIdCurso())
                .titulo(dto.getTitulo())
                .ponderacion(dto.getPonderacion())
                .fecha(dto.getFecha())
                .build();
        log.info("Creando nueva evaluación: {}", dto.getTitulo());
        return evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> listarPorCurso(Long idCurso) {
        return evaluacionRepository.findByIdCurso(idCurso);
    }

    @Transactional
    public void eliminarEvaluacion(Long id) {
        if (!evaluacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evaluación no encontrada");
        }
        evaluacionRepository.deleteById(id);
        log.info("Evaluación {} eliminada", id);
    }

    // FLUJO HÍBRIDO (Guarda la nota y avisa asincrónicamente al estudiante)
    @Transactional
    public Calificacion calificarEstudiante(Long idEvaluacion, CalificacionRequestDTO dto) {
        if (!evaluacionRepository.existsById(idEvaluacion)) {
            throw new ResourceNotFoundException("Evaluación no encontrada");
        }

        Calificacion calificacion = Calificacion.builder()
                .idEvaluacion(idEvaluacion)
                .idEstudiante(dto.getIdEstudiante())
                .nota(dto.getNota())
                .feedback(dto.getFeedback())
                .build();
        
        Calificacion guardada = calificacionRepository.save(calificacion);
        log.info("Estudiante {} calificado en evaluación {}. Nota: {}", dto.getIdEstudiante(), idEvaluacion, dto.getNota());

        // Despacha evento asincrónico para Notificación
        eventPublisher.publishEvent(new EvaluacionCalificadaEvent(this, idEvaluacion, dto.getIdEstudiante(), dto.getNota()));

        return guardada;
    }

    public List<Calificacion> listarNotasPorEstudiante(Long idEstudiante) {
        return calificacionRepository.findByIdEstudiante(idEstudiante);
    }

    @Transactional
    public Calificacion modificarCalificacion(Long idNota, CalificacionRequestDTO dto) {
        Calificacion existente = calificacionRepository.findById(idNota)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada"));
        
        existente.setNota(dto.getNota());
        existente.setFeedback(dto.getFeedback());
        log.info("Calificación {} modificada. Nueva nota: {}", idNota, dto.getNota());
        return calificacionRepository.save(existente);
    }
}