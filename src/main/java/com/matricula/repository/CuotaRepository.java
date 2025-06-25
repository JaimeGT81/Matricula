package com.matricula.repository;

import com.matricula.entity.Cuota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CuotaRepository
        extends BaseRepository<Cuota, String> {

    /** sólo cuotas no pagadas de un alumno */
    Page<Cuota> findByAlumnoDniAlumAndPagadoFalse(
            String dniAlum, Pageable pageable);
}
