package com.duoc.LearningPlatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Convierte a esta clase en el interceptor global de errores
public class GlobalExceptionHandler {

    // 1. Maneja cuando buscamos un ID que no existe (Devuelve 404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleResourceNotFound(ResourceNotFoundException ex) {
        String mensaje = ex.getMessage();
        String tipoRecurso = extraerTipoRecurso(mensaje);
        String mensajePersonalizado = String.format(
            "No se encontró el %s solicitado. Verifique el ID proporcionado y vuelva a intentar.",
            tipoRecurso
        );
        
        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                mensajePersonalizado,
                List.of("Detalle técnico: " + mensaje)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    // Método auxiliar para extraer el tipo de recurso del mensaje de error
    private String extraerTipoRecurso(String mensaje) {
        if (mensaje.contains("Usuario")) return "usuario";
        if (mensaje.contains("Curso")) return "curso";
        if (mensaje.contains("Inscripción")) return "inscripción";
        if (mensaje.contains("Evaluación")) return "evaluación";
        if (mensaje.contains("Pago")) return "pago";
        return "recurso";
    }

    // 2. Maneja cuando fallan las validaciones @NotBlank, @Email, etc. (Devuelve 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Extraemos todos los mensajes de error de los campos que fallaron
        List<String> erroresDetallados = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> String.format("Campo '%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        String mensajeGeneral = String.format(
            "Se encontraron %d error(es) de validación en los datos enviados. Revise los campos indicados a continuación.",
            erroresDetallados.size()
        );

        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                mensajeGeneral,
                erroresDetallados
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    // 3. Maneja cualquier otro error inesperado (Devuelve 500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGeneralException(Exception ex) {
        String mensajeUsuario = "Ocurrió un error interno en el servidor. El equipo técnico ha sido notificado. Por favor, intente más tarde.";
        
        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                mensajeUsuario,
                List.of(
                    "Tipo de error: " + ex.getClass().getSimpleName(),
                    "Mensaje técnico: " + ex.getMessage()
                )
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}