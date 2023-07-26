package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoPoliticoRepository extends JpaRepository<PartidoPolitico, Long> {
    // Métodos personalizados para buscar por nombre y estatus
    List<PartidoPolitico> findByNombre(String nombre);

    List<PartidoPolitico> findByEstatus(String estatus);
}
