package com.duoc.LearningPlatform.repository;

import com.duoc.LearningPlatform.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByIdInscripcion(Long idInscripcion);
}