package com.tfg.rollroutes.controller;

import com.tfg.rollroutes.model.Usuario;
import com.tfg.rollroutes.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class LoginController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(String email, String password, Model model, HttpSession session) {

        Usuario usuario = repository.findByEmail(email);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {

            //Guardamos el usuario en la sesion
            session.setAttribute("usuarioLogueado", usuario);

            return "redirect:/";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(Usuario usuario, Model model) {

        //validamos que no vengan vacios
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getEmail() == null || usuario.getEmail().isEmpty() ||
                usuario.getPassword() == null || usuario.getPassword().isEmpty()) {

            model.addAttribute("error", "Todos los campos son obligatorios");
            return "registro";
        }

        //Validar Formato Email
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            model.addAttribute("error", "Formato de email no válido");
            return "registro";
        }

        //No email duplicado
        Usuario existente = repository.findByEmail(usuario.getEmail());

        if (existente != null) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        usuario.setRol("USER");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);

        return "redirect:/login";
    }
}