package com.matricula.controller;

import com.matricula.entity.Curso;
import com.matricula.repository.CursoRepository;
import com.matricula.service.CursoService;
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

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/cursos")
    public String listCursos(Model model,
                             @RequestParam(defaultValue="0") int page,
                             @RequestParam(defaultValue="8") int size) {
        Pageable p = PageRequest.of(page, size);
        Page<Curso> pageCursos = cursoService.findAllActive(p);
        model.addAttribute("cursos", pageCursos);
        model.addAttribute("newCurso", new Curso());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageCursos.getTotalPages());
        return "cursos";
    }

    @PostMapping("/cursos")
    public String crearCurso(@ModelAttribute Curso curso, Principal principal) {
        LocalDateTime ahora = LocalDateTime.now();

        curso.setFechaRegistro(ahora);
        curso.setUsuarioRegistro(principal.getName());
        curso.setFechaUltModificacion(ahora);
        curso.setUsuarioUltModificacion(principal.getName());
        curso.setEstadoCurso(true);

        // Llamada al servicio en lugar de repo.save()
        cursoService.save(curso, principal.getName());
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/editar/{codigo}")
    public String actualizarCurso(@ModelAttribute Curso curso,
                                  @PathVariable("codigo") String codigo,
                                  Principal principal) {
        LocalDateTime ahora = LocalDateTime.now();

        curso.setCodCurso(codigo);
        curso.setFechaUltModificacion(ahora);
        curso.setUsuarioUltModificacion(principal.getName());

        // Ahora usamos el servicio para versionar correctamente
        cursoService.save(curso, principal.getName());
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/eliminar/{codigo}")
    public String deleteCurso(@PathVariable String codigo, Principal principal) {
        cursoService.softDelete(codigo, principal.getName());
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/reactivar/{codigo}")
    public String undoDeleteCurso(@PathVariable String codigo, Principal principal) {
        cursoService.reactivate(codigo, principal.getName());
        return "redirect:/cursos";
    }

}
