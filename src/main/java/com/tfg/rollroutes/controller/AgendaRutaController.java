package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.*;
import com.tfg.rollroutes.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AgendaRutaController {

    private final AgendaRutaRepository agendaRutaRepository;
    private final RutaRepository rutaRepository;

    public AgendaRutaController(AgendaRutaRepository agendaRutaRepository,
                                RutaRepository rutaRepository) {
        this.agendaRutaRepository = agendaRutaRepository;
        this.rutaRepository = rutaRepository;
    }

    //Formulario
    @GetMapping("/agenda/crear")
    public String mostrarFormulario(Model model) {

        model.addAttribute("rutas", rutaRepository.findAll());

        return "crear-evento";
    }

    //Crear evento
    @PostMapping("/agenda/crear")
    public String crearEvento(@RequestParam Long rutaId,
                              @RequestParam String fecha,
                              HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        Ruta ruta = rutaRepository.findById(rutaId).orElse(null);

        AgendaRuta evento = new AgendaRuta();

        evento.setRuta(ruta);
        evento.setCreador(usuario);
        evento.setFecha(java.time.LocalDate.parse(fecha));

        agendaRutaRepository.save(evento);

        return "redirect:/";
    }

    @GetMapping("/agenda/unirse/{id}")
    public String unirseEvento(@PathVariable Long id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        AgendaRuta evento = agendaRutaRepository.findById(id).orElse(null);

        boolean yaApuntado = evento.getParticipantes().stream().anyMatch(u -> u.getId().equals(usuario.getId()));

        if (!yaApuntado) {
            evento.getParticipantes().add(usuario);
            agendaRutaRepository.save(evento);
        }

        return "redirect:/";
    }

    @GetMapping("/agenda/salir/{id}")
    public String salirEvento(@PathVariable Long id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        AgendaRuta evento = agendaRutaRepository.findById(id).orElse(null);

        if (evento != null && usuario != null) {

            evento.getParticipantes().removeIf(u -> u.getId().equals(usuario.getId()));

            agendaRutaRepository.save(evento);
        }

        return "redirect:/";
    }

    @GetMapping("/agenda/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        AgendaRuta evento = agendaRutaRepository.findById(id).orElse(null);

        if (evento != null && usuario != null) {

            boolean esCreador = evento.getCreador().getId().equals(usuario.getId());
            boolean esAdmin = "ADMIN".equals(usuario.getRol());

            if (esCreador || esAdmin) {
                agendaRutaRepository.delete(evento);
            }
        }

        return "redirect:/";
    }
}
