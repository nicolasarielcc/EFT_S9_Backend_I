package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.TareaEntregadaEvent;
import com.duoc.LearningPlatform.dto.TareaProcesadaEvent;
import com.duoc.LearningPlatform.model.Entrega;
import com.duoc.LearningPlatform.repository.EntregaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class TareaWorker {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // FLUJO HÍBRIDO - FASE ASÍNCRONA (Worker de Análisis)
    @Async
    @EventListener
    public void procesarEntregaEnBackground(TareaEntregadaEvent event) {
        log.info("=== [WORKER ASYNC] Iniciando análisis pesado de la entrega {} ===", event.getIdEntrega());
        
        try {
            // Simulamos que el análisis de plagio y antivirus tarda 3 segundos
            Thread.sleep(3000);
            
            Entrega entrega = entregaRepository.findById(event.getIdEntrega()).orElseThrow();
            
            // Simular resultados
            double plagio = Math.round(new Random().nextDouble() * 15 * 100.0) / 100.0; // Random entre 0% y 15%
            entrega.setPlagio(plagio);
            entrega.setAntivirus("LIMPIO");
            entrega.setEstado("COMPLETADA"); // Estado final asincrónico
            
            entregaRepository.save(entrega);
            
            log.info("=== [WORKER ASYNC] Análisis completado. Plagio: {}%, Antivirus: {}. ===", plagio, entrega.getAntivirus());
            
            // Avisar al servicio de notificaciones
            eventPublisher.publishEvent(new TareaProcesadaEvent(this, entrega.getId(), "COMPLETADA"));

        } catch (InterruptedException e) {
            log.error("Error en el worker asincrónico: {}", e.getMessage());
        }
    }
}