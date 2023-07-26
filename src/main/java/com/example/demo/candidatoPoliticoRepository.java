package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface candidatoPoliticoRepository extends JpaRepository<CandidatoPolitico, Long> {
    List<CandidatoPolitico> findByPartidoPoliticoId(Long partidoPoliticoId);

    @Query("SELECT DISTINCT c.puesto FROM CandidatoPolitico c")
    List<String> findAllDistinctPuestos();

    @Query("SELECT c FROM CandidatoPolitico c WHERE c.puesto = :puesto")
    List<CandidatoPolitico> findNombresByPuesto(@Param("puesto") String puesto);

    // Otros métodos según tus necesidades
}
