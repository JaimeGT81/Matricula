package com.matricula.service.impl;

import com.matricula.dto.SeccionDto;
import com.matricula.dto.AlumnoDTO;
import com.matricula.entity.Alumno;
import com.matricula.entity.Seccion;
import com.matricula.repository.*;
import com.matricula.service.BaseService;
import com.matricula.service.SeccionService;
import com.matricula.entity.SeccionAlumno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final BaseService<Seccion,String> baseService;
    private final SeccionAlumnoRepository seccionAlumnoRepository;
    private final AlumnoRepository alumnoRepository;

    public SeccionServiceImpl(SeccionRepository seccionRepository,
                              DocenteRepository docenteRepository,
                              CursoRepository cursoRepository,
                              SeccionAlumnoRepository seccionAlumnoRepository,
                              AlumnoRepository alumnoRepository) {
        this.seccionRepository = seccionRepository;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
        this.seccionAlumnoRepository = seccionAlumnoRepository;
        this.alumnoRepository = alumnoRepository;

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
                copy.setMaxParticipantes(entity.getMaxParticipantes());
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
        dto.setMaxParticipantes(s.getMaxParticipantes());
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
        s.setMaxParticipantes(dto.getMaxParticipantes());
        return s;
    }

    @Override
    public List<AlumnoDTO> getParticipantes(String nrc) {
        return seccionAlumnoRepository.findBySeccion_SeccionNRC(nrc)
                .stream()
                .map(SeccionAlumno::getAlumno)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlumnoDTO> getAlumnosDisponibles(String nrc) {
        List<AlumnoDTO> participantes = getParticipantes(nrc);
        Pageable unpaged = Pageable.unpaged();

        return alumnoRepository.findByEstadoTrue(unpaged)
                .getContent()
                .stream()
                .filter(alumno -> participantes.stream()
                        .noneMatch(p -> p.getDniAlum().equals(alumno.getDniAlum())))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getDniAlum(),
                alumno.getNombres(),
                alumno.getApellidos()
        );
    }

    @Override
    @Transactional
    public void addParticipante(String nrc, String dniAlumno, String username) {
        Seccion seccion = seccionRepository.findById(nrc)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));
        Alumno alumno = alumnoRepository.findById(dniAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (!seccionAlumnoRepository.existsBySeccionAndAlumno(nrc, dniAlumno)) {
            SeccionAlumno seccionAlumno = new SeccionAlumno();
            seccionAlumno.setSeccion(seccion);
            seccionAlumno.setAlumno(alumno);
            seccionAlumno.setEstado(true);
            seccionAlumno.setFechaRegistro(LocalDateTime.now());
            seccionAlumno.setUsuarioRegistro(username);
            seccionAlumnoRepository.save(seccionAlumno);
        }
    }

    @Override
    @Transactional
    public void removeParticipante(String nrc, String dniAlumno, String username) {
        SeccionAlumno seccionAlumno = seccionAlumnoRepository.findBySeccionAndAlumno(nrc, dniAlumno);
        if (seccionAlumno != null) {
            seccionAlumno.setEstado(false);
            seccionAlumno.setUsuarioUltModificacion(username);
            seccionAlumno.setFechaUltModificacion(LocalDateTime.now());
            seccionAlumnoRepository.save(seccionAlumno);
        }
    }

    @Override
    public void updateParticipantes(String nrc, List<String> participantes, String username) {
        Seccion seccion = seccionRepository.findById(nrc)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        // Validate maximum participants
        if (participantes.size() > seccion.getMaxParticipantes()) {
            throw new RuntimeException("Excede el máximo de participantes permitidos: " + seccion.getMaxParticipantes());
        }

        // Get current participants
        List<SeccionAlumno> currentParticipantes = seccionAlumnoRepository.findBySeccion_SeccionNRC(nrc);

        // Deactivate removed participants
        currentParticipantes.forEach(sa -> {
            if (!participantes.contains(sa.getAlumno().getDniAlum())) {
                sa.setEstado(false);
                sa.setUsuarioModificacion(username);
                sa.setFechaModificacion(LocalDateTime.now());
                seccionAlumnoRepository.save(sa);
            }
        });

        // Add new participants
        participantes.forEach(dniAlum -> {
            SeccionAlumno existingParticipante = currentParticipantes.stream()
                    .filter(sa -> sa.getAlumno().getDniAlum().equals(dniAlum))
                    .findFirst()
                    .orElse(null);

            if (existingParticipante != null) {
                // Reactivate if needed
                if (!existingParticipante.getEstado()) {
                    existingParticipante.setEstado(true);
                    existingParticipante.setUsuarioModificacion(username);
                    existingParticipante.setFechaModificacion(LocalDateTime.now());
                    seccionAlumnoRepository.save(existingParticipante);
                }
            } else {
                // Create new participant
                Alumno alumno = alumnoRepository.findById(dniAlum)
                        .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
                SeccionAlumno newParticipante = new SeccionAlumno();
                newParticipante.setSeccion(seccion);
                newParticipante.setAlumno(alumno);
                newParticipante.setEstado(true);
                newParticipante.setUsuarioRegistro(username);
                newParticipante.setFechaRegistro(LocalDateTime.now());
                seccionAlumnoRepository.save(newParticipante);
            }
        });
    }
}