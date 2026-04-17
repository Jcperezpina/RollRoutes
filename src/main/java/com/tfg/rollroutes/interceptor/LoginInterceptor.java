package com.tfg.rollroutes.interceptor;

import com.tfg.rollroutes.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        String uri = request.getRequestURI();

        if (uri.startsWith("/usuarios-web")) {
            if (usuario == null || !"ADMIN".equals(usuario.getRol())) {
                response.sendRedirect("/login");
                return false;
            }
        }

        // Rutas Públicas (no bloquear)
        if (uri.equals("/login") || uri.equals("/logout") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/registro") || uri.startsWith("/images") || uri.equals("/")) {
            return true;
        }

        // Rutas Privadas  (Bloquear)
        if (usuario == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}