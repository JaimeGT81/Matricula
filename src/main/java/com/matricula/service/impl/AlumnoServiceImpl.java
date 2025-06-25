package com.matricula.service.impl;

import com.matricula.entity.Alumno;
import com.matricula.repository.AlumnoRepository;
import com.matricula.service.AlumnoService;
import com.matricula.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final BaseService<Alumno, String> baseService;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
        this.baseService = new BaseServiceImpl<>(alumnoRepository) {
            @Override
            protected Alumno createNewVersion(Alumno entity) {
                Alumno copy = new Alumno();
                copy.setDniAlum(entity.getDniAlum());
                copy.setNombres(entity.getNombres());
                copy.setApellidos(entity.getApellidos());
                copy.setEstado(entity.getActivo());
                return copy;
            }
        };
    }

    @Override
    public Alumno save(Alumno entity, String username) {
        return baseService.save(entity, username);
    }

    @Override
    public void softDelete(String id, String username) {
        baseService.softDelete(id, username);
    }

    @Override
    public Alumno reactivate(String id, String username) {
        return baseService.reactivate(id, username);
    }

    @Override
    public Page<Alumno> findAllActive(Pageable pageable) {
        return baseService.findAllActive(pageable);
    }

    @Override
    public Page<Alumno> findAllInactive(Pageable pageable) {
        return baseService.findAllInactive(pageable);
    }

    @Override
    public List<Alumno> findAlumnosNotInSeccion(String nrc) {
        return alumnoRepository.findAlumnosNotInSeccion(nrc);
    }

    @Override
    public List<Alumno> findAlumnosBySeccion(String nrc) {
        return alumnoRepository.findAlumnosBySeccion(nrc);
    }
}