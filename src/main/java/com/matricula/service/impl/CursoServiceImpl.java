package com.matricula.service.impl;

import com.matricula.entity.Curso;
import com.matricula.repository.CursoRepository;
import com.matricula.service.CursoService;
import com.matricula.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final BaseService<Curso, String> baseService;

    @Autowired
    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
        this.baseService = new BaseServiceImpl<Curso, String>(cursoRepository) {
            @Override
            protected Curso createNewVersion(Curso entity) {
                // Crea aquí la “nueva versión” igual que en Docente
                Curso copy = new Curso();
                copy.setCodCurso(entity.getCodCurso());
                copy.setCodCarrera(entity.getCodCarrera());
                copy.setNomCarrera(entity.getNomCarrera());
                copy.setNomCurso(entity.getNomCurso());
                copy.setCiclo(entity.getCiclo());
                // NO copiamos fechas o usuario de registro:
                // serán asignadas al salvar
                return copy;
            }
        };
    }

    @Override
    public Curso save(Curso entity, String username) {
        return baseService.save(entity, username);
    }

    @Override
    public void softDelete(String id, String username) {
        baseService.softDelete(id, username);
    }

    @Override
    public Curso reactivate(String id, String username) {
        return baseService.reactivate(id, username);
    }

    @Override
    public Page<Curso> findAllActive(Pageable pageable) {
        return baseService.findAllActive(pageable);
    }

    @Override
    public Page<Curso> findAllInactive(Pageable pageable) {
        return baseService.findAllInactive(pageable);
    }
}
