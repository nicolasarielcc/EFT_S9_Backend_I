package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByIdTarea(Long idTarea);
}