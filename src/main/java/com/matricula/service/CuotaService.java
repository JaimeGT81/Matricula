package com.matricula.service;

import com.matricula.entity.Cuota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CuotaService
        extends BaseService<Cuota, String> {

    Cuota findById(String id);
    /** Genera cuota0 (matrícula) + N cuotas mensuales */
    List<Cuota> generarCuotas(
            String dniAlum,
            int cantidad,
            BigDecimal montoMatricula,
            BigDecimal montoMensual,
            String usuario
    );

    /** Devuelve las cuotas pendientes (pagado = false) */
    Page<Cuota> listarPendientes(
            String dniAlum,
            Pageable pageable
    );

    /** Marca la cuota como pagada */
    Cuota pagarCuota(
            String cuotaId,
            String codigoOperacion,
            String usuario
    );
}
