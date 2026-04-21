package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.Ruta;
import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.RutaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RutaWebController {

    private final RutaRepository rutaRepository;

    public RutaWebController(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    @GetMapping("/rutas")
    public String listarRutas(Model model) {
        model.addAttribute("rutas", rutaRepository.findAll());
        return "rutas";
    }

    //Creacion de rutas
    @GetMapping("/rutas/crear")
    public String mostrarFormulario() {
        return "crear-ruta";
    }

    @PostMapping("/rutas/crear")
    public String crearRuta(Ruta ruta, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        ruta.setCreador(usuario);
        rutaRepository.save(ruta);
        return "redirect:/rutas";
    }
}