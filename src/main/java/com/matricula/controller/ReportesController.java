package com.matricula.controller;

import com.matricula.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public String showReportes() {
        return "lista";
    }

    @GetMapping("/alumnos-resumen")
    @ResponseBody
    public List<Map<String, Object>> getAlumnosResumen() {
        return alumnoService.getAlumnosResumen();
    }
}