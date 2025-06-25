package com.matricula.repository;

import com.matricula.entity.Seccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeccionRepository extends BaseRepository<Seccion, String>{
    Page<Seccion> findBySeccionNRCStartingWithIgnoreCaseAndEstadoTrue(String seccionNRC, Pageable pageable);
}