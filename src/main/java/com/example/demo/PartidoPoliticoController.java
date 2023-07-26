package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PartidoPoliticoController {

    @Autowired
    PartidoPoliticoRepository repo;
    @Autowired
    candidatoPoliticoRepository candidatosRepo;

    @GetMapping("/partidospoliticos/{id}")
    public String verDetallesPartido(Model model, @PathVariable Long id) {
        PartidoPolitico pp = repo.findById(id).get();
        model.addAttribute("partidoPolitico", pp);
        model.addAttribute("listaCandidatos", candidatosRepo.findByPartidoPoliticoId(id));
        return "detallesPartido";
    }
}
