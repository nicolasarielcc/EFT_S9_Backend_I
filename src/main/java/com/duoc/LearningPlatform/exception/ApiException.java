package com.duoc.LearningPlatform.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
// Clase para representar la estructura de la respuesta de error de la API
public class ApiException {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<String> errores; // Lista detallada de los campos que fallaron
}