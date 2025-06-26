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
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final BaseService<Alumno, String> baseService;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository, JdbcTemplate jdbcTemplate) {
        this.alumnoRepository = alumnoRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    @Override
    public List<Map<String, Object>> getAlumnosResumen() {
        String sql = """
            SELECT 
            a.dni as dni,
            CONCAT(a.nombres, ' ', a.apellidos) as nombres,
            c.nombre as carrera,
            COUNT(DISTINCT s.seccionNRC) as secciones,
            COALESCE(SUM(cu.monto), 0) as cuotas,
            COUNT(DISTINCT p.id) as pagos
            FROM alumno a
            LEFT JOIN SeccionAlumno sa ON a.dni = sa.dniAlumno
            LEFT JOIN Seccion s ON sa.seccionNRC = s.seccionNRC
            LEFT JOIN Carrera c ON a.cod_carrera = c.id
            LEFT JOIN Cuota cu ON a.dni = cu.dniAlum
            LEFT JOIN Pago p ON cu.id = p.cuota_id
            WHERE a.activo = 1
            GROUP BY a.dni, CONCAT(a.nombres, ' ', a.apellidos), c.nombre
            """;
        return jdbcTemplate.queryForList(sql);
    }
}