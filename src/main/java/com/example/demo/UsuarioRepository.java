package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioVotante, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
    List<UsuarioVotante> findByClave(String clave);
}
