package com.matricula.service;

import com.matricula.entity.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagoService
        extends BaseService<Pago, String> {

    Pago registrarPago(
            String cuotaId,
            String codigoOperacion,
            String usuario
    );

    Page<Pago> findAllByAlumnoDni(String dniAlum, Pageable pageable);

    boolean hasValidPayment(String dniAlum);
}
