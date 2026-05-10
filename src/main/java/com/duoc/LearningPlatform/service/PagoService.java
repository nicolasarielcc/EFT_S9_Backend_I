package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.PagoProcesadoEvent;
import com.duoc.LearningPlatform.dto.PagoRequestDTO;
import com.duoc.LearningPlatform.dto.PagoResponseDTO;
import com.duoc.LearningPlatform.dto.WebhookPagoDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.mapper.PagoMapper;
import com.duoc.LearningPlatform.model.Pago;
import com.duoc.LearningPlatform.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private PagoMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public PagoResponseDTO iniciarPago(PagoRequestDTO request) {
        Pago pago = mapper.toEntity(request);
        Pago guardado = repository.save(pago);
        log.info("Pago ID {} iniciado. Esperando confirmación del banco (Webhook)...", guardado.getId());
        // Retornamos 201 Created inmediato (Sincrónico)
        return mapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPorId(Long id) {
        Pago pago = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        return mapper.toResponse(pago);
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public PagoResponseDTO solicitarReembolso(Long id) {
        Pago pago = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        pago.setEstado("REEMBOLSADO");
        log.info("Pago ID {} reembolsado", id);
        return mapper.toResponse(repository.save(pago));
    }

    // EL WEBHOOK ASINCRÓNICO QUE LLAMA EL BANCO
    @Transactional
    public void procesarConfirmacionBanco(WebhookPagoDTO webhook) {
        Pago pago = repository.findById(webhook.getIdPago())
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        pago.setEstado(webhook.getEstado());
        pago.setReferenciaBanco(webhook.getReferenciaBanco());
        repository.save(pago);
        
        log.info("=== [WEBHOOK BANCO] Pago ID {} confirmado como {} ===", pago.getId(), pago.getEstado());

        // Disparamos evento asincrónico
        eventPublisher.publishEvent(new PagoProcesadoEvent(this, pago.getId(), pago.getIdInscripcion(), pago.getEstado()));
    }
}