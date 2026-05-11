package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.dto.UsuarioRegistradoEvent;
import com.duoc.LearningPlatform.dto.UsuarioRequestDTO;
import com.duoc.LearningPlatform.dto.UsuarioResponseDTO;
import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.mapper.UsuarioMapper;
import com.duoc.LearningPlatform.model.Usuario;
import com.duoc.LearningPlatform.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Anotación de Lombok para habilitar logs
@Service
// Servicio que maneja la lógica de negocio relacionada con los usuarios
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /*
    === Workaround para el hashing de contraseñas para el escenario de entrega de la EFT (No productivo) ===
    Se inicia la utilidad BCrypt, evitando la configuración completa de Spring Security.
    Esto permite aplicar hashing a las contraseñas sin necesidad de integrar todo el framework de seguridad,
    manteniendo el enfoque en la lógica de negocio y la comunicación entre microservicios.
    */ 
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para obtener todos los usuarios, mapeando las entidades a DTOs de respuesta
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        log.info("Consultando todos los usuarios en la base de datos");
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    // Método para obtener un usuario por su ID, lanzando una excepción si no se encuentra
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        log.info("Consultando usuario con ID: {}", id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario con ID {} no encontrado en la base de datos", id);
                    return new ResourceNotFoundException(
                        String.format("Usuario no encontrado. El ID '%d' no corresponde a ningún usuario registrado en el sistema.", id)
                    );
                });
        log.info("Usuario con ID {} consultado exitosamente - Correo: {}", id, usuario.getCorreo());
        return mapper.toResponse(usuario);
    }

    // Método para crear un nuevo usuario, aplicando hashing a la contraseña y publicando un evento de registro
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        log.info("Iniciando creación de nuevo usuario con correo: {}", request.getCorreo());
        
        Usuario entidad = mapper.toEntity(request);
        
        // Aplicamos Hashing a la contraseña antes de guardarla en la base de datos
        String hash = passwordEncoder.encode(entidad.getContrasena());
        entidad.setContrasena(hash);
        
        Usuario guardado = repository.save(entidad);
        log.info("Usuario creado exitosamente con ID: {} - Nombre: {} - Rol: {}", guardado.getId(), guardado.getNombre(), guardado.getRol());

        /*
        COMUNICACIÓN ASINCRÓNICA (SIMULACIÓN DE MESSAGE BROKER):
        Se dispara el evento.
        La petición del usuario termina aquí (rápido), pero el servicio de notificaciones lo procesará en 2do plano.
        */
        log.info("Publicando evento 'UsuarioRegistradoEvent' para el correo: {}", guardado.getCorreo());
        eventPublisher.publishEvent(new UsuarioRegistradoEvent(this, guardado.getCorreo(), guardado.getNombre()));

        return mapper.toResponse(guardado);
    }

    // Método para actualizar un usuario existente, verificando su existencia y aplicando cambios según el request
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {
        log.info("Actualizando usuario con ID: {} - Nuevo correo: {}", id, request.getCorreo());
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    String.format("No se puede actualizar. El usuario con ID '%d' no existe en el sistema.", id)
                ));

        usuarioExistente.setNombre(request.getNombre());
        usuarioExistente.setCorreo(request.getCorreo());
        usuarioExistente.setRol(request.getRol());
        usuarioExistente.setEstado(request.getEstado());

        // Solo reencripta si se envía una contraseña nueva (y no un hash existente)
        if (request.getContrasena() != null && !request.getContrasena().isEmpty() && !request.getContrasena().startsWith("$2a$")) {
            usuarioExistente.setContrasena(passwordEncoder.encode(request.getContrasena()));
            log.info("Contraseña del usuario ID {} actualizada con éxito", id);
        }

        Usuario actualizado = repository.save(usuarioExistente);
        log.info("Usuario ID {} actualizado exitosamente - Nombre: {} - Rol: {}", id, actualizado.getNombre(), actualizado.getRol());
        return mapper.toResponse(actualizado);
    }

    // Método para eliminar un usuario por su ID, verificando su existencia antes de eliminarlo
    @Transactional
    public void eliminar(Long id) {
        log.info("Intentando eliminar usuario con ID: {}", id);
        if (!repository.existsById(id)) {
            log.error("No se puede eliminar. Usuario con ID {} no existe", id);
            throw new ResourceNotFoundException(
                String.format("No se puede eliminar. El usuario con ID '%d' no existe en el sistema.", id)
            );
        }
        // Elimina el usuario de la base de datos
        repository.deleteById(id);
        log.info("Usuario con ID {} eliminado correctamente de la base de datos", id);
    }
}