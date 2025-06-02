package com.matricula.controller;

import com.matricula.entity.Curso;
import com.matricula.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/cursos")
    public String curso(
            Model model,
            @RequestParam(defaultValue = "") String codigoCurso,
            @RequestParam(defaultValue = "") String nombreCurso,
            @RequestParam(defaultValue = "0") int creditos,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Curso> cursos = cursoRepository.findByCriteria(codigoCurso, nombreCurso, creditos, pageable);

        model.addAttribute("cursos", cursos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cursos.getTotalPages());
        model.addAttribute("newCurso", new Curso());

        return "cursos";
    }

    @PostMapping("/cursos")
    public String crearCurso(@ModelAttribute Curso curso, Principal principal) {
        curso.setFechaRegistro(LocalDateTime.now());
        curso.setUsuarioRegistro(principal.getName());
        curso.setFechaUltModificacion(LocalDateTime.now());
        curso.setUsuarioUltModificacion(principal.getName());
        curso.setEstadoCurso(true);

        cursoRepository.save(curso);
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/editar/{codigo}")
    public String actualizarCurso(@ModelAttribute Curso curso, @PathVariable String codigo, Principal principal) {
        curso.setCodCurso(codigo);
        curso.setFechaUltModificacion(LocalDateTime.now());
        curso.setUsuarioUltModificacion(principal.getName());

        cursoRepository.save(curso);
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/eliminar/{codigo}")
    public String eliminarCurso(@PathVariable String codigo) {
        cursoRepository.deleteById(codigo);
        return "redirect:/cursos";
    }

}
