package com.matricula.service;

import com.matricula.dto.DocenteDto;
import com.matricula.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocenteService extends BaseService<Docente, String> {
    Page<DocenteDto> findAll(String dni, String nombre, String apellido, Pageable pageable);
    Page<DocenteDto> findAllActive(String dni, String nombre, String apellido, Pageable pageable);
    void save(DocenteDto docenteFormDto);
    void deleteById(String dni);
    DocenteDto findById(String dni);
}