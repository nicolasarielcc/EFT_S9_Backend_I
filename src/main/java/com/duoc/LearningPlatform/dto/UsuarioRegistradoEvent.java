package com.duoc.LearningPlatform.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * === Descripción de UsuarioRegistradoEvent ===
 * Herramienta nativa de Spring que permite simular la comunicación asincrónica (como si tuviéramos RabbitMQ o Kafka).
 * 
 * Esta clase sirve como "mensaje" que viaja entre el microservicio de Usuarios y el de Notificaciones.
 * Cuando un usuario se registra exitosamente, se crea una instancia de UsuarioRegistradoEvent con el correo y nombre del usuario.
 * 
 * El microservicio de Notificaciones escucha este evento y, al recibirlo, puede enviar un correo de bienvenida al nuevo usuario 
 * utilizando la información proporcionada en el evento.
 * 
 * De esta manera, se logra una comunicación eficiente y desacoplada entre los microservicios, permitiendo que el sistema sea más 
 * escalable y mantenible.
 * 
 * En resumen, UsuarioRegistradoEvent es un componente para facilitar la integración entre los microservicios de Usuarios Notificaciones,
 * asegurando que los nuevos usuarios reciban una experiencia de bienvenida adecuada a través de correos electrónicos 
 * personalizados.
 */

@Getter
// Evento que se dispara cuando un usuario se registra exitosamente
public class UsuarioRegistradoEvent extends ApplicationEvent {
    private final String correo;
    private final String nombre;

    // Constructor del evento, recibe el objeto fuente, correo y nombre del usuario registrado
    public UsuarioRegistradoEvent(Object source, String correo, String nombre) {
        super(source);
        this.correo = correo;
        this.nombre = nombre;
    }
}