package com.duoc.LearningPlatform.controller;

import com.duoc.LearningPlatform.dto.PagoRequestDTO;
import com.duoc.LearningPlatform.dto.PagoResponseDTO;
import com.duoc.LearningPlatform.dto.WebhookPagoDTO;
import com.duoc.LearningPlatform.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PagoController {

    @Autowired
    private PagoService service;

    // --- Endpoints de Usuario (REST) ---
    @PostMapping("/api/pago")
    public ResponseEntity<PagoResponseDTO> iniciarPago(@Valid @RequestBody PagoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.iniciarPago(request));
    }

    @GetMapping("/api/pago")
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/api/pago/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PatchMapping("/api/pago/{id}/reembolso")
    public ResponseEntity<PagoResponseDTO> solicitarReembolso(@PathVariable Long id) {
        return ResponseEntity.ok(service.solicitarReembolso(id));
    }

    // --- Endpoint del Banco (Webhook) ---
    // Según especificación: /webhook/pago/confirmacion
    @PostMapping("/webhook/pago/confirmacion")
    public ResponseEntity<String> confirmacionBanco(@Valid @RequestBody WebhookPagoDTO webhook) {
        service.procesarConfirmacionBanco(webhook);
        return ResponseEntity.ok("Webhook recibido y procesado correctamente");
    }
}