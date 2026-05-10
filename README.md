# 🎓 Plataforma de Aprendizaje en Línea - Solución EFT Final

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen.svg)
![H2 Database](https://img.shields.io/badge/Database-H2-blue.svg)

Este proyecto representa la culminación de la **Evaluación Final Transversal (EFT)** para la asignatura **Desarrollo Backend I (PBY2201)**. Se ha implementado un sistema robusto basado en una arquitectura de microservicios para gestionar una plataforma educativa completa.

## 🏗️ Arquitectura de la Solución

El sistema se basa en un modelo de **Monolito Modular**, donde cada paquete representa un dominio de microservicio independiente con su propia lógica, persistencia y estrategia de comunicación.

### Microservicios Implementados:
1. **msUsuario:** Gestión de autenticación, perfiles y seguridad con Hashing BCrypt.
2. **msCurso:** Administración del catálogo, cupos y categorías de cursos.
3. **msInscripcion:** Control del flujo de registro de estudiantes y estados de matrícula.
4. **msTarea:** Gestión de entregas con **Flujo Híbrido** (Validación REST + Análisis Asincrónico).
5. **msEvaluacion:** Registro de calificaciones y feedback con eventos automáticos.
6. **msPago:** Procesamiento de pagos mediante **Webhooks** y eventos asincrónicos.
7. **msNotificacion:** Consumidor central de eventos para envíos multi-canal (Email/Push).

## 🚀 Innovaciones Técnicas y Cumplimiento de Rúbrica

### 1. Comunicación Asincrónica y Resiliencia
* **Spring Events:** Implementamos `ApplicationEventPublisher` para simular un Message Broker (Kafka/RabbitMQ). Esto permite que procesos pesados como el envío de correos o el análisis de tareas no bloqueen la experiencia del usuario (UX).
* **Anotación @Async:** Los servicios de notificación y análisis procesan mensajes en hilos paralelos, garantizando alta disponibilidad.

### 2. Seguridad y Validación Avanzada
* **Protección de Credenciales:** Implementamos Hashing con `BCrypt` y aseguramos que las contraseñas nunca se expongan en los DTOs de respuesta.
* **Blindaje de API:** Validación estricta de entradas mediante `Bean Validation` (`@Email`, `@NotBlank`, `@Min/Max`) en todos los endpoints.
* **Manejo de Errores Profesional:** Centralización de excepciones en un `@RestControllerAdvice` que devuelve objetos `ApiException` claros y legibles.

### 3. Trazabilidad con Spring AOP
* Uso de **Aspectos** para interceptar peticiones, medir latencias y generar logs estructurados (`@Slf4j`) de forma transversal, facilitando el diagnóstico de incidentes en tiempo real.

## ⚙️ Guía de Uso y Pruebas (Postman)

### Configuración del Entorno
* **Base de Datos:** H2 en memoria (Configurada con HikariCP para eficiencia).
* **Consola H2:** `http://localhost:8080/h2-console` (JDBC: `jdbc:h2:mem:cursodb`).
* **Usuario BD:** `sa` (Sin contraseña).

### Flujos Críticos para Demostración:
* **Registro Seguro:** `POST /api/usuarios` (Verificar que la contraseña se guarda como Hash).
* **Inscripción y Pago (Webhooks):**
    1. `POST /api/inscripcion` (Crea estado PENDIENTE_PAGO).
    2. `POST /webhook/pago/confirmacion` (Simula respuesta del banco).
    3. El sistema activa automáticamente la inscripción mediante eventos.
* **Entrega de Tareas (Híbrido):**
    1. `POST /api/tarea/{id}/entrega` (Respuesta inmediata 201 Created).
    2. Observar logs: El "Worker" inicia el análisis de plagio en background.

## 📁 Estructura de Paquetes

~~~text
src/main/java/com/duoc/LearningPlatform/
├── aop/          # Aspectos (Logging transversal y métricas)
├── controller/   # Endpoints RESTful (Entrada del sistema)
├── dto/          # Objetos de transferencia y eventos asincrónicos
├── exception/    # Manejo global de errores (ApiException)
├── mapper/       # Transformación DTO <-> Entity
├── model/        # Entidades JPA (Persistencia en H2)
├── repository/   # Acceso a datos (Spring Data JPA)
└── service/      # Lógica de negocio y Workers asincrónicos
~~~

---

### ✅ Checklist de Rúbrica Cumplida:
* [x] **Arquitectura:** Diseñada con más de 4 microservicios identificados y comunicados.
* [x] **Configuración:** Más de 5 dependencias clave (Web, JPA, AOP, Actuator, Validation).
* [x] **Servicios:** Lógica de negocio distribuida en capas con manejo de excepciones.
* [x] **CRUD:** Implementación completa de operaciones para todas las entidades.
* [x] **Persistencia:** Conexión a base de datos con acceso eficiente vía HikariCP.