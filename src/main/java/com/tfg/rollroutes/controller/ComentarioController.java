package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.*;
import com.tfg.rollroutes.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;
    private final AgendaRutaRepository agendaRutaRepository;

    public ComentarioController(ComentarioRepository comentarioRepository,
                                AgendaRutaRepository agendaRutaRepository) {
        this.comentarioRepository = comentarioRepository;
        this.agendaRutaRepository = agendaRutaRepository;
    }

    @PostMapping("/comentario/crear")
    public String crearComentario(@RequestParam Long eventoId,
                                  @RequestParam String texto,
                                  HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        AgendaRuta evento = agendaRutaRepository.findById(eventoId).orElse(null);

        if (usuario != null && evento != null) {

            Comentario comentario = new Comentario();
            comentario.setTexto(texto);
            comentario.setFecha(LocalDate.now());
            comentario.setUsuario(usuario);
            comentario.setEvento(evento);

            comentarioRepository.save(comentario);
        }

        return "redirect:/";
    }
}