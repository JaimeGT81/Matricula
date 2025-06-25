package com.matricula.controller;

import com.matricula.dto.SeccionDto;
import com.matricula.repository.AlumnoRepository;
import com.matricula.repository.CursoRepository;
import com.matricula.repository.DocenteRepository;
import com.matricula.repository.SeccionAlumnoRepository;
import com.matricula.service.SeccionService;
import com.matricula.entity.Alumno;
import com.matricula.service.AlumnoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/secciones")
public class SeccionController {

    private final SeccionService seccionService;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final AlumnoRepository alumnoRepository;
    private final AlumnoService alumnoService;
    private final SeccionAlumnoRepository seccionAlumnoRepository;

    public SeccionController(SeccionService seccionService,
                             DocenteRepository docenteRepository,
                             CursoRepository cursoRepository,
                             AlumnoRepository alumnoRepository,
                             AlumnoService alumnoService,
                             SeccionAlumnoRepository seccionAlumnoRepository){
        this.seccionService = seccionService;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.alumnoService = alumnoService;
        this.seccionAlumnoRepository = seccionAlumnoRepository;
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
    private String updateSeccion(@ModelAttribute SeccionDto seccion, Model model, @PathVariable String nrc, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            long currentParticipantes = seccionAlumnoRepository.countParticipantesBySeccion(nrc);
            if(seccion.getMaxParticipantes() < currentParticipantes) {
                redirectAttributes.addFlashAttribute("mensajeErrorEditar", "El número máximo de participantes no puede ser menor que los actuales (" + currentParticipantes + ").");
                redirectAttributes.addFlashAttribute("abrirModalEditar", true);
                redirectAttributes.addFlashAttribute("nrcEditar", nrc);
            }
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
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeErrorEditar", e.getMessage());
            redirectAttributes.addFlashAttribute("abrirModalEditar", true);
            redirectAttributes.addFlashAttribute("nrcEditar", nrc);
            return "redirect:/secciones";
        }
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

    @GetMapping("/seccion-alumnos/{nrc}")
    @ResponseBody
    public ResponseEntity<Map<String, List<Alumno>>> getAlumnosSeccion(@PathVariable String nrc) {
        try {
            Map<String, List<Alumno>> result = new HashMap<>();

            // Get all available students (not in this section)
            List<Alumno> disponibles = alumnoService.findAlumnosNotInSeccion(nrc);
            result.put("disponibles", disponibles);

            // Get students in this section
            List<Alumno> participantes = alumnoService.findAlumnosBySeccion(nrc);
            result.put("participantes", participantes);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/seccion-alumnos/{nrc}/save")
    @ResponseBody
    public ResponseEntity<?> saveAlumnosSeccion(
            @PathVariable String nrc,
            @RequestBody List<String> participantes, Principal principal) {
        try {
            seccionService.updateParticipantes(nrc, participantes, principal.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}