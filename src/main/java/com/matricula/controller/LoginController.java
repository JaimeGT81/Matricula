package com.matricula.controller;

import com.matricula.dto.LoginForm;
import com.matricula.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador para manejo de login.
 */
@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping({"/", "/login"})
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("loginForm") LoginForm form,
                          Model model,
                          HttpSession session) {
        if (authService.authenticate(form)) {
            // Guardar usuario en sesión
            session.setAttribute("user", form.getUsername());
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Usuario o contraseña inválidos");
            return "login";
        }
    }
}