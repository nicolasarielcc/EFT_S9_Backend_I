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
        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Maneja cuando fallan las validaciones @NotBlank, @Email, etc. (Devuelve 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Extraemos todos los mensajes de error de los campos que fallaron
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en la validación de los datos enviados. Por favor, revise los campos.",
                errores
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    // 3. Maneja cualquier otro error inesperado (Devuelve 500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGeneralException(Exception ex) {
        ApiException error = new ApiException(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado en el servidor",
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}