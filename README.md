# 🎓 Plataforma de Aprendizaje en Línea - Backend API

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen.svg)
![H2 Database](https://img.shields.io/badge/Database-H2-blue.svg)

Este proyecto corresponde a la **Evaluación Final Transversal (EFT) de la Semana 9** para la asignatura Desarrollo Backend I (PBY2201). Implementa el backend de una plataforma de aprendizaje en línea, aplicando principios de arquitectura de microservicios, seguridad, y buenas prácticas de desarrollo empresarial.

## 🏗️ Arquitectura del Proyecto

El proyecto está diseñado bajo el patrón de **Monolito Modular**. Esta estrategia permite simular la separación y comunicación de múltiples microservicios (Usuarios, Cursos, Notificaciones, etc.) dentro de un único ecosistema Spring Boot, facilitando las pruebas y el despliegue en entornos académicos, pero manteniendo las fronteras lógicas de dominio.

### Características Principales:
* **Separación de Capas:** Estricta división entre `Controllers`, `Services`, `Repositories` y `Mappers`.
* **Patrón DTO (Data Transfer Object):** Aislamiento de las entidades de base de datos (`@Entity`) mediante DTOs para solicitudes y respuestas, evitando la sobreexposición de datos sensibles (ej. contraseñas).
* **Validación de Datos:** Uso de `spring-boot-starter-validation` (`@NotBlank`, `@Email`, etc.) en la capa de entrada.
* **Manejo Global de Excepciones:** Implementación de `@RestControllerAdvice` para capturar errores y retornar respuestas legibles y estructuradas (`ApiException`), eliminando los stacktraces de Tomcat expuestos al cliente.

## 🚀 Tecnologías y Herramientas Destacadas

* **Spring Data JPA & Hibernate:** Para la persistencia de datos y el mapeo objeto-relacional (ORM).
* **Base de Datos H2:** Base de datos en memoria para el entorno de desarrollo, gestionada a través de un pool de conexiones **HikariCP**.
* **Spring AOP (Aspect-Oriented Programming):** Utilizado para interceptar peticiones a nivel de controlador (`GlobalAspect`) y registrar tiempos de ejecución de manera transversal sin alterar la lógica de negocio.
* **Spring Events (@Async):** Implementación del patrón Publicador/Suscriptor nativo de Spring para **simular la comunicación asincrónica** entre dominios (ej. simulando un Message Broker como RabbitMQ o Kafka para el envío de notificaciones en background).
* **BCrypt (Spring Security Crypto):** *Workaround* implementado para aplicar hashing seguro a las contraseñas en la base de datos sin necesidad de habilitar el filtro completo de Spring Security, facilitando las pruebas REST.
* **Lombok:** Para reducir el código repetitivo (boilerplate) mediante anotaciones como `@Data`, `@Builder` y `@Slf4j`.

## 📁 Estructura del Proyecto

~~~text
src/main/java/com/duoc/LearningPlatform/
├── aop/          # Aspectos transversales (Trazabilidad y Logging)
├── controller/   # Endpoints REST expuestos al cliente
├── dto/          # Objetos de transferencia de datos con reglas de validación
├── exception/    # Interceptor global y excepciones personalizadas
├── mapper/       # Traductores bidireccionales entre DTOs y Entities
├── model/        # Entidades JPA (Esquema de BD)
├── repository/   # Interfaces de acceso a datos (Spring Data)
└── service/      # Lógica de negocio y publicación de eventos
~~~

## ⚙️ Instrucciones de Ejecución

1. Clonar o descargar el repositorio.
2. Abrir una terminal en la raíz del proyecto.
3. Compilar el proyecto usando el Wrapper de Maven:
   > ./mvnw clean compile
4. Levantar el servidor embebido (Tomcat):
   > ./mvnw spring-boot:run
5. La API estará disponible en: `http://localhost:8080/`

### 🗄️ Consola H2 (Base de Datos en Memoria)
Una vez que la aplicación esté corriendo, puedes inspeccionar las tablas generadas automáticamente ingresando a:
* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:cursodb`
* **Username:** `adminEFT`
* **Password:** `EFT2026_backend_I`

## 📡 Endpoints Principales (Ejemplos de Pruebas)

Puedes importar las peticiones en Postman apuntando a `http://localhost:8080`:

* **Usuarios**
  * `POST /api/usuarios` - Registrar un nuevo usuario (Valida formato de correo y encripta contraseña).
  * `GET /api/usuarios` - Listar todos los usuarios (Oculta las contraseñas).
* **Cursos**
  * `POST /api/cursos` - Crear un nuevo curso.
  * `GET /api/cursos` - Listar cursos disponibles.
* **Actuator (Monitoreo)**
  * `GET /actuator/health` - Estado de salud de la aplicación y base de datos.
  * `GET /actuator/info` - Información del microservicio.