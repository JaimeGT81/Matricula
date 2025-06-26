package com.matricula.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

/**
 * Controlador para la página de inicio.
 */
@Controller
public class InicioController {

    @GetMapping("/inicio")
    public String showInicio(Principal principal, Authentication authentication, Model model) {
        boolean isAdmin = false;
        // si llegaste aquí, ya estás autenticado
        if (authentication != null) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        return "inicio";
    }
}