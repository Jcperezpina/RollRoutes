package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.AgendaRuta;
import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.AgendaRutaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final AgendaRutaRepository agendaRutaRepository;

    public HomeController(AgendaRutaRepository agendaRutaRepository) {
        this.agendaRutaRepository = agendaRutaRepository;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        List<AgendaRuta> eventos = agendaRutaRepository.findAll();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        Map<Long, Boolean> usuarioApuntado = new HashMap<>();

        if (usuario != null) {
            for (AgendaRuta evento : eventos) {

                boolean esta = evento.getParticipantes().stream()
                        .anyMatch(u -> u.getId().equals(usuario.getId()));

                usuarioApuntado.put(evento.getId(), esta);
            }
        }

        // Proximos eventos
        List<AgendaRuta> proximos = eventos.stream()
                .filter(e -> !e.getFecha().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        // Eventos pasados
        List<AgendaRuta> pasados = eventos.stream()
                .filter(e -> e.getFecha().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        model.addAttribute("proximos", proximos);
        model.addAttribute("pasados", pasados);
        model.addAttribute("usuarioApuntado", usuarioApuntado);

        return "home";
    }
}