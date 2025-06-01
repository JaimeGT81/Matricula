package com.matricula.controller;

import java.time.LocalDateTime;

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

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

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
        Page<Alumno> alumnos = alumnoRepository
                .findByDniAlumStartingWithIgnoreCaseAndNombresStartingWithIgnoreCaseAndApellidosStartingWithIgnoreCase(
                        dni, nombres, apellidos, pageable);

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", alumnos.getTotalPages());

        model.addAttribute("newAlumno", new Alumno());
        model.addAttribute("ubigeos", ubigeoRepository.findAll());
        return "alumnos";
    }

    @PostMapping("/alumnos")
    public String crearAlumno(@ModelAttribute Alumno alumno, Principal principal) {

        // Aquí puedes inicializar campos como fechaRegistro, usuarioRegistro, activo, etc.
        alumno.setFechaRegistro(LocalDateTime.now());
        alumno.setUsuarioRegistro(principal.getName()); // Cambia por usuario real si tienes seguridad
        alumno.setFechaUltModificacion(LocalDateTime.now());
        alumno.setUsuarioUltModificacion(principal.getName());
        alumno.setActivo(true);

        alumnoRepository.save(alumno);

        return "redirect:/alumnos"; // Redirige a la lista después de crear
    }

    @PostMapping("/alumnos/editar/{dni}")
    public String actualizarAlumno(@ModelAttribute Alumno alumno, @PathVariable String dni, Principal principal) {
        alumno.setDniAlum(dni);
        alumno.setFechaUltModificacion(LocalDateTime.now());
        alumno.setUsuarioUltModificacion(principal.getName()); // Reemplaza por el usuario real

        alumnoRepository.save(alumno); // save() funciona como update si el ID existe

        return "redirect:/alumnos";
    }

    @PostMapping("/alumnos/eliminar/{dni}")
    public String eliminarAlumno(@PathVariable String dni) {
        alumnoRepository.deleteById(dni);
        return "redirect:/alumnos";
    }
}