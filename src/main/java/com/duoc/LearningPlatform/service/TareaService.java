package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.EntregaRequestDTO;
import com.duoc.LearningPlatform.dto.TareaEntregadaEvent;
import com.duoc.LearningPlatform.dto.TareaRequestDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.Entrega;
import com.duoc.LearningPlatform.model.Tarea;
import com.duoc.LearningPlatform.repository.EntregaRepository;
import com.duoc.LearningPlatform.repository.TareaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public Tarea crearTarea(TareaRequestDTO dto) {
        Tarea tarea = Tarea.builder()
                .idCurso(dto.getIdCurso())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaEntrega(dto.getFechaEntrega())
                .build();
        return tareaRepository.save(tarea);
    }

    public List<Tarea> listarTareasPorCurso(Long idCurso) {
        return tareaRepository.findByIdCurso(idCurso);
    }

    // FLUJO HÍBRIDO - FASE SÍNCRONA
    @Transactional
    public Entrega entregarTarea(Long idTarea, EntregaRequestDTO dto) {
        log.info("Recibiendo entrega de estudiante {} para la tarea {}", dto.getIdEstudiante(), idTarea);
        
        if (!tareaRepository.existsById(idTarea)) {
            throw new ResourceNotFoundException("Tarea no encontrada");
        }

        // 1. Respuesta Inmediata (Estado: ENTREGADA)
        Entrega entrega = Entrega.builder()
                .idTarea(idTarea)
                .idEstudiante(dto.getIdEstudiante())
                .archivo(dto.getArchivo())
                .comentario(dto.getComentario())
                .estado("ENTREGADA") // Estado sincrónico inicial
                .build();
        
        Entrega guardada = entregaRepository.save(entrega);
        log.info("Entrega {} guardada. Respuesta inmediata al cliente enviada.", guardada.getId());

        // 2. Despachar análisis a segundo plano (Async)
        log.info("Publicando evento 'TareaEntregadaEvent' para análisis en background...");
        eventPublisher.publishEvent(new TareaEntregadaEvent(this, guardada.getId(), idTarea, dto.getIdEstudiante()));

        return guardada;
    }

    public List<Entrega> listarEntregasPorTarea(Long idTarea) {
        return entregaRepository.findByIdTarea(idTarea);
    }
}