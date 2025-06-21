package com.matricula.controller;

import com.matricula.dto.DocenteDto;
import com.matricula.repository.CarreraRepository;
import com.matricula.repository.UbigeoRepository;
import com.matricula.service.DocenteService;
import com.matricula.service.UploadFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/docentes")
public class DocenteController {

    private final DocenteService docenteService;
    private final CarreraRepository carreraRepository;
    private final UbigeoRepository ubigeoRepository;
    private final UploadFileService uploadFileService;

    @Autowired
    public DocenteController(DocenteService docenteService,
                             CarreraRepository carreraRepository,
                             UbigeoRepository ubigeoRepository,
                             UploadFileService uploadFileService) {
        this.docenteService    = docenteService;
        this.carreraRepository = carreraRepository;
        this.ubigeoRepository  = ubigeoRepository;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping
    private String listDocentes(Model model,
                                @RequestParam(defaultValue = "") String dni,
                                @RequestParam(defaultValue = "") String nombre,
                                @RequestParam(defaultValue = "") String apellido,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<DocenteDto> docenteListDtos = docenteService.findAllActive(dni,nombre,apellido,pageable);
        model.addAttribute("docentes",docenteListDtos);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",docenteListDtos.getTotalPages());
        model.addAttribute("newDocente",new DocenteDto());
        model.addAttribute("carreras", carreraRepository.findAll());
        model.addAttribute("ubigeos", ubigeoRepository.findAll()); // TODO: Falta implementar el selector encadenado (próximo sprint)

        /* Paginación en caso de buscar por filtros */
        model.addAttribute("dni", dni);
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);

        return "docentes";
    }

    @PostMapping
    private String createDocente(@ModelAttribute DocenteDto docente, Principal principal){
        MultipartFile nuevaFoto = docente.getFotoNueva();
        if (nuevaFoto != null && !nuevaFoto.isEmpty() &&
                nuevaFoto.getOriginalFilename() != null &&
                !nuevaFoto.getOriginalFilename().isBlank()){
            docente.setFotoActual(uploadFileService.copy(docente.getFotoNueva(), "docente"));
        }
        docente.setUsuarioRegistro((String) principal.getName());
        docente.setFechaRegistro(LocalDateTime.now());
        docente.setEstado(true);
        docenteService.save(docente);
        return "redirect:/docentes";
    }

    @PostMapping("/editar/{dni}")
    private String updateDocente(@ModelAttribute DocenteDto docente, @PathVariable String dni, Principal principal) {
        MultipartFile nuevaFoto = docente.getFotoNueva();

        // Procesar nueva foto solo si se subió una válida
        if (nuevaFoto != null && !nuevaFoto.isEmpty() &&
                nuevaFoto.getOriginalFilename() != null &&
                !nuevaFoto.getOriginalFilename().isBlank()) {

            // Borrar foto anterior si existe
            if (docente.getFotoActual() != null && !docente.getFotoActual().isEmpty()) {
                uploadFileService.delete(docente.getFotoActual(), "docente");
            }

            // Subir nueva foto
            docente.setFotoActual(uploadFileService.copy(nuevaFoto, "docente"));

        }else if (docente.getFotoActual() != null && docente.getFotoActual().isEmpty()) {
            // Normalizar fotoActual: convertir cadena vacía a null
            docente.setFotoActual(null);
        }

        docente.setDni(dni);
        docente.setEstado(docente.getEstado() != null ? docente.getEstado() : false);
        docente.setUsuarioModficacion((String) principal.getName());
        docente.setFechaModificacion(LocalDateTime.now());

        docenteService.save(docente);
        return "redirect:/docentes";
    }

    /**
     * Soft delete: marca como inactivo en lugar de borrar físicamente
     */
    @PostMapping("/eliminar/{dni}")
    public String deleteDocente(@PathVariable String dni,
                                Principal principal) {
        // Si la foto debe borrarse al hacer soft-delete, mantenlo aquí.
        DocenteDto dto = docenteService.findById(dni);
        if (dto != null && dto.getFotoActual() != null) {
            uploadFileService.delete(dto.getFotoActual(), "docente");
        }
        // Llamamos a softDelete en lugar de deleteById
        docenteService.softDelete(dni, principal.getName());
        return "redirect:/docentes";
    }

    /**
     * (Opcional) Reactivar registro eliminado
     */
    @PostMapping("/reactivar/{dni}")
    public String reactivateDocente(@PathVariable String dni,
                                    Principal principal) {
        docenteService.reactivate(dni, principal.getName());
        return "redirect:/docentes";
    }
}