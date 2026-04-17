package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioWebController {

    private final UsuarioRepository repository;

    public UsuarioWebController(UsuarioRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/usuarios-web")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", repository.findAll());
        return "usuarios";
    }

    @PostMapping("/usuarios-web")
    public String crearUsuario(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/usuarios-web";
    }

    @GetMapping("/usuarios-web/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = repository.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        return "editar-usuario";
    }

    @PostMapping("/usuarios-web/editar")
    public String guardarEdicion(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/usuarios-web";
    }
    @GetMapping("/usuarios-web/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/usuarios-web";
    }
}