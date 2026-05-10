package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByIdCurso(Long idCurso);
}