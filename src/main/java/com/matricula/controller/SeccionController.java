package com.matricula.controller;

import com.matricula.dto.SeccionDto;
import com.matricula.repository.AlumnoRepository;
import com.matricula.repository.CursoRepository;
import com.matricula.repository.DocenteRepository;
import com.matricula.service.SeccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/secciones")
public class SeccionController {

    private final SeccionService seccionService;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final AlumnoRepository alumnoRepository;

    public SeccionController(SeccionService seccionService, DocenteRepository docenteRepository, CursoRepository cursoRepository, AlumnoRepository alumnoRepository){
        this.seccionService = seccionService;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @GetMapping
    private String listSecciones(Model model,
                                @RequestParam(defaultValue = "") String nrc,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size){
        loadCommonData(model,nrc,page,size,null);
        model.addAttribute("nrc", nrc);
        return "secciones";
    }

    @PostMapping
    private String createSeccion(@ModelAttribute SeccionDto seccion, Model model, Principal principal) {
        String mensajeError = seccionService.validateSeccion(seccion);
        if (mensajeError != null) {
            loadCommonData(model, "", 0,8,seccion);
            model.addAttribute("mensajeErrorNuevo", mensajeError);
            model.addAttribute("abrirModalNuevo", true);
            return "secciones";
        }
        seccion.setUsuarioRegistro(principal.getName());
        seccion.setFechaRegistro(LocalDateTime.now());
        seccion.setEstado(true);
        seccionService.save(seccion);
        return "redirect:/secciones";
    }

    @PostMapping("/editar/{nrc}")
    private String updateSeccion(@ModelAttribute SeccionDto seccion,Model model, @PathVariable String nrc, Principal principal) {
        String mensajeError = seccionService.validateSeccion(seccion);
        if(mensajeError != null){
            loadCommonData(model, "", 0,8,seccion);
            model.addAttribute("mensajeErrorEditar", mensajeError);
            model.addAttribute("abrirModalEditar", true);
            model.addAttribute("nrcEditar", nrc);
            return "secciones";
        }
        seccion.setNrc(nrc);
        seccion.setEstado(seccion.getEstado() != null ? seccion.getEstado() : false);
        seccion.setUsuarioModificacion((String) principal.getName());
        seccion.setFechaModificacion(LocalDateTime.now());
        seccionService.save(seccion);
        return "redirect:/secciones";
    }

    @PostMapping("/eliminar/{nrc}")
    public String deleteSeccion(@PathVariable String nrc,
                                Principal principal) {
        seccionService.softDelete(nrc, principal.getName());
        return "redirect:/secciones";
    }

    /* Carga los datos comunes para get post y update */
    private void loadCommonData(Model model, String nrc, int page, int size, SeccionDto seccion){
        Pageable pageable = PageRequest.of(page, size);
        Page<SeccionDto> secciones = seccionService.findAllActive(nrc,pageable);
        model.addAttribute("secciones", secciones);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", secciones.getTotalPages());
        model.addAttribute("newSeccion", seccion != null ? seccion : new SeccionDto());
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("docentes", docenteRepository.findAll());
        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("nrc", nrc);
    }
}