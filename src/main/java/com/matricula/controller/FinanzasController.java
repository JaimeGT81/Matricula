package com.matricula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finanzas")
public class FinanzasController {

    /**
     * GET /finanzas
     * Muestra el dashboard de Finanzas con enlaces a Cuotas y Pagos.
     */
    @GetMapping
    public String finanzasHome() {
        return "finanzas";
    }
}
