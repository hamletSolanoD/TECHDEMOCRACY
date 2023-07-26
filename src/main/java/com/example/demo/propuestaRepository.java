package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface propuestaRepository extends JpaRepository<propuesta, Long> {
    // Métodos personalizados para buscar por nombre y estatus
    List<propuesta> findByCandidatoId(Long candidatoId);

}
