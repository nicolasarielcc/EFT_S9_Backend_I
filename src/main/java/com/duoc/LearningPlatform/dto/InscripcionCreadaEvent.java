package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InscripcionCreadaEvent extends ApplicationEvent {
    private final Long idInscripcion;
    private final Long idEstudiante;
    private final Long idCurso;

    public InscripcionCreadaEvent(Object source, Long idInscripcion, Long idEstudiante, Long idCurso) {
        super(source);
        this.idInscripcion = idInscripcion;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
    }
}