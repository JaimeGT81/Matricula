package com.matricula.repository;

import com.matricula.entity.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagoRepository
        extends BaseRepository<Pago, String> {
    Page<Pago> findByCuotaAlumnoDniAlum(String dniAlum, Pageable pageable);
    boolean existsByCuota_Alumno_DniAlum(String dniAlum);

}
