package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByIdEstudiante(Long idEstudiante);
    List<Inscripcion> findByIdCurso(Long idCurso);
}