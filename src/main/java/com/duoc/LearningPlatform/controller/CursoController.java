package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.dto.CursoRequestDTO;
import com.duoc.LearningPlatform.dto.CursoResponseDTO;
import com.duoc.LearningPlatform.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gestionar cursos
@RestController
@RequestMapping("/api/cursos")
// Permite solicitudes desde cualquier origen (CORS)
public class CursoController {

    // Inyección del servicio de cursos
    @Autowired
    private CursoService service;

    // Endpoint para obtener todos los cursos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // Endpoint para obtener un curso por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // Endpoint para crear un nuevo curso
    @PostMapping
    public ResponseEntity<CursoResponseDTO> crear(@Valid @RequestBody CursoRequestDTO request) {
        CursoResponseDTO creado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Endpoint para actualizar un curso existente
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    // Endpoint para eliminar un curso por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}