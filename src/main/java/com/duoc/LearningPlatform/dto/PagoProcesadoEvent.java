package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PagoProcesadoEvent extends ApplicationEvent {
    private final Long idPago;
    private final Long idInscripcion;
    private final String estado; // APROBADO o RECHAZADO

    public PagoProcesadoEvent(Object source, Long idPago, Long idInscripcion, String estado) {
        super(source);
        this.idPago = idPago;
        this.idInscripcion = idInscripcion;
        this.estado = estado;
    }
}