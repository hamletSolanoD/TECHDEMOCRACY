package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class principalController {
    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private PartidoPoliticoRepository poliPartRepo;

    @Autowired
    private candidatoPoliticoRepository candidatoPoliticoRepository;

    @GetMapping("/")
    public String inicio() {

        return "index"; // Nombre de la plantilla Thymeleaf
    }

    @GetMapping("/entradadelportal")
    public String principal(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Obtener el objeto UsuarioVotante de la sesión si existe
        if (session != null) {
            UsuarioVotante usuarioVotante = (UsuarioVotante) session.getAttribute("usuarioAutenticado");
            List<String> puestosList = candidatoPoliticoRepository.findAllDistinctPuestos();
            Map<String, List<CandidatoPolitico>> candidatosPorPuesto = new HashMap<>();
            for (String puesto : puestosList) {
                List<CandidatoPolitico> candidatos = candidatoPoliticoRepository.findNombresByPuesto(puesto);
                candidatosPorPuesto.put(puesto, candidatos);
            }
            model.addAttribute("puestosList", puestosList);
            model.addAttribute("candidatosPorPuesto", candidatosPorPuesto);
            model.addAttribute("usuarioVotante", usuarioVotante);
            model.addAttribute("respuestasForm", new RespuestasForm());
            model.addAttribute("status", usuarioVotante.getStatus());
        } else {
            // error
        }

        model.addAttribute("PartidosPoliticos", poliPartRepo.findAll());

        return "entradadelportal"; // Nombre de la plantilla Thymeleaf
    }

    @PostMapping("/iniciosesion")
    public String iniciosesion(@RequestParam("password") String password, HttpServletRequest request) {
        UsuarioVotante votante = null;
        try {
            votante = repo.findByClave(password).get(0);
        } catch (Throwable e) {
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("usuarioAutenticado", votante);

        return "redirect:/entradadelportal";
    }

}
