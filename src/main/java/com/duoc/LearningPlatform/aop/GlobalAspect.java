package com.duoc.LearningPlatform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // Indica que esta clase es un Aspecto de AOP (Programación Orientada a Aspectos)
@Component
@Slf4j
// Este aspecto se encargará de interceptar las llamadas a los métodos de los controladores para medir su tiempo de ejecución y registrar logs
public class GlobalAspect {

    // Define DÓNDE queremos interceptar: "En cualquier método dentro del paquete controller"
    @Pointcut("within(com.duoc.LearningPlatform.controller..*)")
    public void controllerPointcut() {}

    // Define QUÉ hacer: "Rodear" (Around) la ejecución del método para medir su tiempo
    @Around("controllerPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        
        log.info(">>>> [AOP] Interceptando petición hacia el endpoint: {}", methodName);
        
        // Ejecutamos el método original del controlador y capturamos su resultado
        Object proceed;
        try {
            // Dejamos que el controlador ejecute su lógica normal
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            // Si ocurre un error, lo registramos en log
            log.error("<<<< [AOP] Error en {}: {}", methodName, e.getMessage());
            throw e; // Lanzamos el error para que lo capture GlobalExceptionHandler
        }
        
        // Calculamos el tiempo que tomó ejecutar el método y lo registramos en log
        long executionTime = System.currentTimeMillis() - start;
        log.info("<<<< [AOP] Endpoint {} respondió exitosamente en {} ms", methodName, executionTime);
        
        return proceed;
    }
}