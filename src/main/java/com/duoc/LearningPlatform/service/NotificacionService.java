package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.UsuarioRegistradoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificacionService {

    /*
    La anotación @Async hace que este método escuche eventos y los procese en un 
    hilo paralelo, simulando cómo un microservicio consume mensajes de RabbitMQ o Kafka.
    */
    @Async
    @EventListener
    // Método que maneja el evento de usuario registrado, simulando el envío de un correo de bienvenida
    public void manejarUsuarioRegistrado(UsuarioRegistradoEvent event) {
        log.info("==========================================================");
        log.info("[EVENTO ASINCRÓNICO RECIBIDO] - msNotificacion procesando...");
        log.info("Simulando envío de email de bienvenida a: {}", event.getCorreo());
        log.info("Cuerpo del correo: Hola {}, ¡Bienvenido a la Plataforma de Aprendizaje!", event.getNombre());
        log.info("==========================================================");
    }
}