package com.matricula.controller;

import com.matricula.entity.Pago;
import com.matricula.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * GET /pagos
     * Lista paginada de todos los pagos, filtrada por DNI de alumno.
     */
    @GetMapping
    public String listarPagos(
            @RequestParam(name = "dniAlum", required = false, defaultValue = "") String dniAlum,
            @RequestParam(name = "page",    required = false, defaultValue = "0")  int page,
            @RequestParam(name = "size",    required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaRegistro").descending());
        Page<Pago> pagos = pagoService.findAllByAlumnoDni(dniAlum, pageable);

        model.addAttribute("pagos",      pagos);
        model.addAttribute("dniAlum",    dniAlum);
        model.addAttribute("currentPage", pagos.getNumber());
        model.addAttribute("totalPages",  pagos.getTotalPages());
        return "pagos";
    }
}
