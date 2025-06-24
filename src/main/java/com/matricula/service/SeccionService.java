package com.matricula.service;

import com.matricula.dto.SeccionDto;
import com.matricula.entity.Seccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeccionService extends BaseService<Seccion, String>{
    Page<SeccionDto> findAllActive(String nrc, Pageable pageable);
    void save(SeccionDto seccionDto);

    String validateSeccion(SeccionDto seccionDto);
}