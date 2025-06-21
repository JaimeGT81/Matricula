package com.matricula.controller;

import java.time.LocalDateTime;

import com.matricula.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.matricula.entity.Alumno;
import com.matricula.repository.AlumnoRepository;
import com.matricula.repository.UbigeoRepository;
import java.security.Principal;

@Controller
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final UbigeoRepository ubigeoRepository;

    @Autowired
    public AlumnoController(AlumnoService alumnoService, UbigeoRepository ubigeoRepository) {
        this.alumnoService = alumnoService;
        this.ubigeoRepository = ubigeoRepository;
    }

    @GetMapping({"/alumnos"})
    public String alumno(
            Model model,
            @RequestParam(defaultValue = "") String dni,
            @RequestParam(defaultValue = "") String nombres,
            @RequestParam(defaultValue = "") String apellidos,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Alumno> alumnos = alumnoService.findAllActive(pageable);

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", alumnos.getTotalPages());
        model.addAttribute("newAlumno", new Alumno());
        model.addAttribute("ubigeos", ubigeoRepository.findAll());
        return "alumnos";
    }

    @PostMapping("/alumnos")
    public String crearAlumno(@ModelAttribute Alumno alumno, Principal principal) {

        alumno.setEstado(true);
        alumnoService.save(alumno, principal.getName());
        return "redirect:/alumnos"; // Redirige a la lista después de crear
    }

    @PostMapping("/alumnos/editar/{dni}")
    public String actualizarAlumno(@ModelAttribute Alumno alumno, @PathVariable String dni, Principal principal) {
        alumno.setDniAlum(dni);
        alumnoService.save(alumno, principal.getName()); // save() funciona como update si el ID existe

        return "redirect:/alumnos";
    }

    @PostMapping("/alumnos/eliminar/{dni}")
    public String eliminarAlumno(@PathVariable String dni, Principal principal) {
        alumnoService.softDelete(dni,principal.getName());
        return "redirect:/alumnos";
    }
}