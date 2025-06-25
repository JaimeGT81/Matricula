package com.matricula.controller;

import com.matricula.entity.Cuota;
import com.matricula.entity.Pago;
import com.matricula.service.CuotaService;
import com.matricula.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cuotas")
public class CuotaController {

    private final CuotaService cuotaService;

    @Autowired
    public CuotaController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    /**
     * GET /cuotas
     * Lista paginada de cuotas (pendientes o pagadas), filtradas por DNI de alumno.
     */
    @GetMapping
    public String listarCuotas(
            @RequestParam(name = "dniAlum", required = false, defaultValue = "") String dniAlum,
            @RequestParam(name = "page",    required = false, defaultValue = "0")  int page,
            @RequestParam(name = "size",    required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaGeneracion").ascending());
        Page<Cuota> cuotas = cuotaService.listarPendientes(dniAlum, pageable);

        model.addAttribute("cuotas",   cuotas);
        model.addAttribute("dniAlum",  dniAlum);
        model.addAttribute("currentPage", cuotas.getNumber());
        model.addAttribute("totalPages",  cuotas.getTotalPages());
        return "cuotas";
    }

    /**
     * POST /cuotas/generar
     * Genera cuota 0 + N cuotas mensuales para un alumno.
     */
    @PostMapping("/generar")
    public String generarCuotas(
            @RequestParam("dniAlum")          String dniAlum,
            @RequestParam("cantidad")         int cantidad,
            @RequestParam("montoMatricula")   BigDecimal montoMatricula,
            @RequestParam("montoMensual")     BigDecimal montoMensual,
            Principal principal
    ) {
        cuotaService.generarCuotas(
                dniAlum,
                cantidad,
                montoMatricula,
                montoMensual,
                principal.getName()
        );
        return "redirect:/cuotas?dniAlum=" + dniAlum;
    }

    /**
     * POST /cuotas/pagar/{id}
     * Marca una cuota como pagada y registra el pago (voucher).
     */
    @PostMapping("/pagar/{id}")
    public String pagarCuota(
            @PathVariable("id")           String cuotaId,
            @RequestParam("codigoOperacion") String codigoOperacion,
            Principal principal
    ) {
        cuotaService.pagarCuota(cuotaId, codigoOperacion, principal.getName());
        // para recargar la misma lista filtrada por DNI:
        Cuota c = cuotaService.findById(cuotaId);
        String dni = c.getAlumno().getDniAlum();
        return "redirect:/cuotas?dniAlum=" + dni;
    }
}

