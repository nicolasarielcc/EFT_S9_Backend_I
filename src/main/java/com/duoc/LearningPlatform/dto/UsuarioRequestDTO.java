package com.duoc.LearningPlatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    //Validaciones para el DTO de solicitud de usuario
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    //Validación para el correo electrónico
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe proporcionar un formato de correo válido")
    private String correo;

    //Validación para la contraseña
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    //Validación para el rol del usuario
    @NotBlank(message = "El rol es obligatorio (ESTUDIANTE, ACADEMICO, ADMIN)")
    private String rol;

    //Validación para el estado del usuario
    @NotBlank(message = "El estado es obligatorio (ACTIVO, BLOQUEADO, INACTIVO)")
    private String estado;
}