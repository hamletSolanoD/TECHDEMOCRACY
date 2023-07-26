package com.example.demo;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private candidatoPoliticoRepository candidatoPoliticoRepository;

    @GetMapping("/politicoselegidos")
    public String politicoselegidos(Model model, HttpServletRequest request) {
        // Obtener todos los puestos disponibles
        List<String> puestosList = candidatoPoliticoRepository.findAllDistinctPuestos();

        // Crear el Map para almacenar los candidatos con mayor puntaje por puesto
        Map<String, CandidatoPolitico> candidatosElegidos = new HashMap<>();

        // Para cada puesto, obtener los candidatos y encontrar el candidato con mayor
        // puntaje
        for (String puesto : puestosList) {
            List<CandidatoPolitico> candidatosPorPuesto = candidatoPoliticoRepository.findNombresByPuesto(puesto);

            // Encontrar el candidato con mayor puntaje
            CandidatoPolitico candidatoConMayorPuntaje = null;
            long mayorPuntaje = 0;
            for (CandidatoPolitico candidato : candidatosPorPuesto) {
                long puntajeCandidato = candidato.getVotos() != null ? candidato.getVotos() : 0;
                if (puntajeCandidato > mayorPuntaje) {
                    mayorPuntaje = puntajeCandidato;
                    candidatoConMayorPuntaje = candidato;
                }
            }

            // Agregar el candidato con mayor puntaje al Map
            if (candidatoConMayorPuntaje != null) {
                candidatosElegidos.put(puesto, candidatoConMayorPuntaje);
            }
        }

        // Pasar el Map candidatosElegidos al modelo para que pueda ser utilizado en la
        // vista
        model.addAttribute("candidatosElegidos", candidatosElegidos);

        return "politicoselegidos";
    }

    @PostMapping("/submitRespuestas")
    public String submitRespuestas(@ModelAttribute RespuestasForm respuestasForm, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            UsuarioVotante usuarioVotante = (UsuarioVotante) session.getAttribute("usuarioAutenticado");
            usuarioVotante.setStatus("NO");
            repo.save(usuarioVotante);

            for (CandidatoPolitico candidato : respuestasForm.getRespuestas().values()) {
                candidato.setVotos(candidato.getVotos() + 1);
                candidatoPoliticoRepository.save(candidato);
            }
        }

        return "redirect:/entradadelportal";
    }

}