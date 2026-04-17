package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return repository.save(usuario);
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
}