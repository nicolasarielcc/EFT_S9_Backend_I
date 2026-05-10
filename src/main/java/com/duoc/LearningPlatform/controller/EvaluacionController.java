package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.dto.CalificacionRequestDTO;
import com.duoc.LearningPlatform.dto.EvaluacionRequestDTO;
import com.duoc.LearningPlatform.model.Calificacion;
import com.duoc.LearningPlatform.model.Evaluacion;
import com.duoc.LearningPlatform.service.EvaluacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    @PostMapping
    public ResponseEntity<Evaluacion> crearEvaluacion(@Valid @RequestBody EvaluacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearEvaluacion(request));
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Evaluacion>> listarPorCurso(@PathVariable Long idCurso) {
        return ResponseEntity.ok(service.listarPorCurso(idCurso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        service.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints de Calificaciones ---

    @PostMapping("/{id}/calificar")
    public ResponseEntity<Calificacion> calificarEstudiante(@PathVariable Long id, @Valid @RequestBody CalificacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.calificarEstudiante(id, request));
    }

    // Simula /api/evaluacion/notas/yo
    @GetMapping("/notas/estudiante/{idEstudiante}")
    public ResponseEntity<List<Calificacion>> listarNotasPorEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(service.listarNotasPorEstudiante(idEstudiante));
    }

    @PutMapping("/{id}/calificacion/{idNota}")
    public ResponseEntity<Calificacion> modificarCalificacion(@PathVariable Long id, @PathVariable Long idNota, @Valid @RequestBody CalificacionRequestDTO request) {
        // En una API real, aquí validaríamos que la 'idNota' pertenezca a la 'id' de evaluación.
        return ResponseEntity.ok(service.modificarCalificacion(idNota, request));
    }
}