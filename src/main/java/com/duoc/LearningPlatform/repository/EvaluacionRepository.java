package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByIdCurso(Long idCurso);
}