package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EvaluacionCalificadaEvent extends ApplicationEvent {
    private final Long idEvaluacion;
    private final Long idEstudiante;
    private final Double nota;

    public EvaluacionCalificadaEvent(Object source, Long idEvaluacion, Long idEstudiante, Double nota) {
        super(source);
        this.idEvaluacion = idEvaluacion;
        this.idEstudiante = idEstudiante;
        this.nota = nota;
    }
}