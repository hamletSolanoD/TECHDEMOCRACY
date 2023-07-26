package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CandidatoPoliticoController {

    @Autowired
    private candidatoPoliticoRepository candidatoPoliticoRepository;
    @Autowired
    private propuestaRepository propRepo;

    @GetMapping("/candidatospoliticos/{id}")
    public String verDetallesCandidato(@PathVariable Long id, Model model) {
        CandidatoPolitico candidato = candidatoPoliticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidato no encontrado con ID: " + id));

        model.addAttribute("candidato", candidato);
        model.addAttribute("propuestasPoliticas", propRepo.findByCandidatoId(id));

        return "propuestapolitica"; // Nombre de la plantilla Thymeleaf para mostrar los detalles del candidato
    }
}