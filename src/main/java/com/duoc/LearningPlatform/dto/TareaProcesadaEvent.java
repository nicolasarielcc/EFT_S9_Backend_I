package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TareaProcesadaEvent extends ApplicationEvent {
    private final Long idEntrega;
    private final String estado;

    public TareaProcesadaEvent(Object source, Long idEntrega, String estado) {
        super(source);
        this.idEntrega = idEntrega;
        this.estado = estado;
    }
}