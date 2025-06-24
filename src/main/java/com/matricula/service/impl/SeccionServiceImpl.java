package com.matricula.service.impl;

import com.matricula.dto.SeccionDto;
import com.matricula.entity.Seccion;
import com.matricula.repository.CursoRepository;
import com.matricula.repository.DocenteRepository;
import com.matricula.repository.SeccionRepository;
import com.matricula.service.BaseService;
import com.matricula.service.SeccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final BaseService<Seccion,String> baseService;

    public SeccionServiceImpl(SeccionRepository seccionRepository, DocenteRepository docenteRepository, CursoRepository cursoRepository){
        this.seccionRepository = seccionRepository;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;

        this.baseService = new BaseServiceImpl<Seccion, String>(seccionRepository) {
            @Override
            protected Seccion createNewVersion(Seccion entity) {
                Seccion copy = new Seccion();
                copy.setSeccionNRC(entity.getSeccionNRC());
                copy.setCurso(entity.getCurso());
                copy.setDocente(entity.getDocente());
                copy.setIdAulaDR(entity.getIdAulaDR());
                copy.setFechaInicio(entity.getFechaInicio());
                copy.setDiaSemana(entity.getDiaSemana());
                copy.setHoraInicio(entity.getHoraInicio());
                copy.setHoraFin(entity.getHoraFin());
                copy.setModalidad(entity.getModalidad());
                return copy;
            }
        };
    }

    @Override
    public Seccion save(Seccion entity, String username) {
        return baseService.save(entity,username);
    }

    @Override
    public void softDelete(String id, String username) {
        baseService.softDelete(id,username);
    }

    @Override
    public Seccion reactivate(String id, String username) {
        return baseService.reactivate(id,username);
    }

    @Override
    public Page<Seccion> findAllActive(Pageable pageable) {
        return baseService.findAllActive(pageable);
    }

    @Override
    public Page<Seccion> findAllInactive(Pageable pageable) {
        return baseService.findAllInactive(pageable);
    }

    @Override
    public Page<SeccionDto> findAllActive(String nrc, Pageable pageable) {
        Page<Seccion> page = seccionRepository.findBySeccionNRCStartingWithIgnoreCaseAndEstadoTrue(nrc, pageable);
        return page.map(this::convertToDto);
    }

    @Override
    public void save(SeccionDto dto) {
        Seccion entidad = convertToEntity(dto);
        seccionRepository.save(entidad);
    }

    /* Validaciones */
    @Override
    public String validateSeccion(SeccionDto dto) {
        if (dto.getFechaInicio() != null && dto.getFechaInicio().isBefore(LocalDate.now())) {
            return "La fecha de inicio no puede ser anterior a hoy.";
        } else if (dto.getHoraInicio() == null || dto.getHoraFin() == null) {
            return "Debe ingresar la hora de inicio y fin.";
        } else if (!dto.getHoraInicio().isBefore(dto.getHoraFin())) {
            return "La hora de inicio debe ser antes de la hora de fin.";
        } else if (java.time.Duration.between(dto.getHoraInicio(), dto.getHoraFin()).toHours() > 4) {
            return "La duración máxima es de 4 horas.";
        }
        return null;
    }

    private SeccionDto convertToDto(Seccion s){
        SeccionDto dto = new SeccionDto();
        dto.setNrc(s.getSeccionNRC());
        dto.setCodCurso(s.getCurso().getCodCurso());
        dto.setDniDocente(s.getDocente().getDniDocente());
        dto.setIdAulaDR(s.getIdAulaDR());
        dto.setFechaInicio(s.getFechaInicio());
        dto.setDiaSemana(s.getDiaSemana());
        dto.setHoraInicio(s.getHoraInicio());
        dto.setHoraFin(s.getHoraFin());
        dto.setModalidad(s.getModalidad());
        dto.setEstado(s.getEstadoSeccion());
        dto.setFechaRegistro(s.getFechaRegistro());
        dto.setUsuarioRegistro(s.getUsuarioRegistro());
        dto.setFechaModificacion(s.getFechaUltModificacion());
        dto.setUsuarioModificacion(s.getUsuarioUltModificacion());
        return dto;
    }

    private Seccion convertToEntity(SeccionDto dto){
        Seccion s = new Seccion();
        s.setSeccionNRC(dto.getNrc());
        docenteRepository.findById(dto.getDniDocente()).ifPresent(s::setDocente);
        cursoRepository.findById(dto.getCodCurso()).ifPresent(s::setCurso);
        s.setIdAulaDR(dto.getIdAulaDR());
        s.setFechaInicio(dto.getFechaInicio());
        s.setDiaSemana(dto.getFechaInicio().getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("es","ES")));
        s.setHoraInicio(dto.getHoraInicio());
        s.setHoraFin(dto.getHoraFin());
        s.setModalidad(dto.getModalidad());
        s.setEstadoSeccion(dto.getEstado());
        s.setFechaRegistro(dto.getFechaRegistro());
        s.setUsuarioRegistro(dto.getUsuarioRegistro());
        s.setFechaUltModificacion(dto.getFechaModificacion());
        s.setUsuarioUltModificacion(dto.getUsuarioModificacion());
        return s;
    }
}