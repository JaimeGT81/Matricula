package com.matricula.service;

import com.matricula.entity.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AlumnoService {
    Alumno save(Alumno entity, String username);
    void softDelete(String id, String username);
    Alumno reactivate(String id, String username);
    Page<Alumno> findAllActive(Pageable pageable);
    Page<Alumno> findAllInactive(Pageable pageable);
    List<Alumno> findAlumnosNotInSeccion(String nrc);
    List<Alumno> findAlumnosBySeccion(String nrc);
    List<Map<String, Object>> getAlumnosResumen();

}
