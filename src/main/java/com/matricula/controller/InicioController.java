package com.matricula.controller;

import jakarta.servlet.http.HttpSession;
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
    public String showInicio(Principal principal, Model model) {
        // si llegaste aquí, ya estás autenticado
        String username = principal.getName();
        model.addAttribute("username", username);
        return "inicio";
    }
}