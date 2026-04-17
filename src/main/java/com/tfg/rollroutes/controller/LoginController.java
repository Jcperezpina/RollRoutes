package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UsuarioRepository repository;

    public LoginController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(String email, String password, Model model) {

        Usuario usuario = repository.findByEmail(email);

        if (usuario != null && usuario.getPassword().equals(password)) {
            return "redirect:/usuarios-web";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }
}