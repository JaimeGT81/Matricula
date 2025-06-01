package com.matricula.service;

import com.matricula.dto.DocenteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocenteService {
    Page<DocenteDto> findAll(String dni, String nombre, String apellido, Pageable pageable);
    void save(DocenteDto docenteFormDto);
    void deleteById(String dni);
    DocenteDto findById(String dni);
}