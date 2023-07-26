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
    private PartidoPoliticoRepository poliPartRepo;

    @Autowired
    private candidatoPoliticoRepository candidatoPoliticoRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        System.out.println("main inicio de sesion ");
        return "/inicio"; // Nombre de la plantilla Thymeleaf
    }

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

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request) {
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

        return "/main"; // Nombre de la plantilla Thymeleaf
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

        return "redirect:/main";
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

        return "redirect:/main";
    }

}