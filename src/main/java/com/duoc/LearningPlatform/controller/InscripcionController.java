package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.dto.InscripcionRequestDTO;
import com.duoc.LearningPlatform.dto.InscripcionResponseDTO;
import com.duoc.LearningPlatform.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crear(@Valid @RequestBody InscripcionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<InscripcionResponseDTO>> listarPorEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(service.listarPorEstudiante(idEstudiante));
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<InscripcionResponseDTO>> listarPorCurso(@PathVariable Long idCurso) {
        return ResponseEntity.ok(service.listarPorCurso(idCurso));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<InscripcionResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(service.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}