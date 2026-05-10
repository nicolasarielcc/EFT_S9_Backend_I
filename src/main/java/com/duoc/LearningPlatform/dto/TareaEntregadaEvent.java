package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TareaEntregadaEvent extends ApplicationEvent {
    private final Long idEntrega;
    private final Long idTarea;
    private final Long idEstudiante;

    public TareaEntregadaEvent(Object source, Long idEntrega, Long idTarea, Long idEstudiante) {
        super(source);
        this.idEntrega = idEntrega;
        this.idTarea = idTarea;
        this.idEstudiante = idEstudiante;
    }
}