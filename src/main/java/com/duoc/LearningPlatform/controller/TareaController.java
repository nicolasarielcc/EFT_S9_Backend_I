package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.dto.EntregaRequestDTO;
import com.duoc.LearningPlatform.dto.TareaRequestDTO;
import com.duoc.LearningPlatform.model.Entrega;
import com.duoc.LearningPlatform.model.Tarea;
import com.duoc.LearningPlatform.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarea")
public class TareaController {

    @Autowired
    private TareaService service;

    @PostMapping
    public ResponseEntity<Tarea> crear(@Valid @RequestBody TareaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTarea(request));
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Tarea>> listarPorCurso(@PathVariable Long idCurso) {
        return ResponseEntity.ok(service.listarTareasPorCurso(idCurso));
    }

    // El endpoint del Flujo Híbrido
    @PostMapping("/{id}/entrega")
    public ResponseEntity<Entrega> entregarTarea(@PathVariable Long id, @Valid @RequestBody EntregaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.entregarTarea(id, request));
    }

    @GetMapping("/{id}/entregas")
    public ResponseEntity<List<Entrega>> listarEntregas(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarEntregasPorTarea(id));
    }
}