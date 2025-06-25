package com.matricula.service;

import com.matricula.dto.AlumnoDTO;
import com.matricula.dto.SeccionDto;
import com.matricula.entity.Seccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeccionService {
    Seccion save(Seccion entity, String username);
    void softDelete(String id, String username);
    Seccion reactivate(String id, String username);
    Page<Seccion> findAllActive(Pageable pageable);
    Page<Seccion> findAllInactive(Pageable pageable);
    Page<SeccionDto> findAllActive(String nrc, Pageable pageable);
    void save(SeccionDto dto);
    String validateSeccion(SeccionDto dto);
    List<AlumnoDTO> getParticipantes(String nrc);
    List<AlumnoDTO> getAlumnosDisponibles(String nrc);
    void addParticipante(String nrc, String dniAlumno, String username);
    void removeParticipante(String nrc, String dniAlumno, String username);
    void updateParticipantes(String nrc, List<String> participantes, String username);
}