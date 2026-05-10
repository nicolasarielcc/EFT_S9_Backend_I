package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByIdEstudiante(Long idEstudiante);
    List<Calificacion> findByIdEvaluacion(Long idEvaluacion);
}