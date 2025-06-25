package com.matricula.controller;

import com.matricula.dto.AlumnoDTO;
import com.matricula.entity.Alumno;
import com.matricula.entity.Seccion;
import com.matricula.entity.SeccionAlumno;
import com.matricula.repository.AlumnoRepository;
import com.matricula.repository.SeccionRepository;
import com.matricula.repository.SeccionAlumnoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seccion-alumnos")
public class SeccionAlumnoController {

    private final AlumnoRepository alumnoRepository;
    private final SeccionAlumnoRepository seccionAlumnoRepository;
    private final SeccionRepository seccionRepository;

    public SeccionAlumnoController(
            AlumnoRepository alumnoRepository,
            SeccionAlumnoRepository seccionAlumnoRepository,
            SeccionRepository seccionRepository) {
        this.alumnoRepository = alumnoRepository;
        this.seccionAlumnoRepository = seccionAlumnoRepository;
        this.seccionRepository = seccionRepository;
    }

    @GetMapping("/{nrc}")
    public ResponseEntity<Map<String, List<AlumnoDTO>>> getAlumnos(@PathVariable String nrc) {
        List<Alumno> disponiblesEntities = alumnoRepository.findAlumnosNotInSeccion(nrc);
        List<Alumno> participantesEntities = alumnoRepository.findAlumnosBySeccion(nrc);

        List<AlumnoDTO> disponibles = disponiblesEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        List<AlumnoDTO> participantes = participantesEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, List<AlumnoDTO>> response = new HashMap<>();
        response.put("disponibles", disponibles);
        response.put("participantes", participantes);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{nrc}/save")
    @Transactional
    public ResponseEntity<?> saveParticipantes(@PathVariable String nrc, @RequestBody List<String> dniAlumnos) {
        try {
            Optional<Seccion> seccionOpt = seccionRepository.findById(nrc);
            if (seccionOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Seccion seccion = seccionOpt.get();

            // Validar maximo de participantes
            if (dniAlumnos.size() > seccion.getMaxParticipantes()) {
                return ResponseEntity.badRequest().body("El número de alumnos supera el máximo permitido.");
            }

            seccionAlumnoRepository.deleteBySeccion_SeccionNRC(nrc);

            for (String dniAlumno : dniAlumnos) {
                Optional<Alumno> alumnoOpt = alumnoRepository.findById(dniAlumno);
                if (alumnoOpt.isPresent()) {
                    SeccionAlumno sa = new SeccionAlumno();
                    sa.setSeccion(seccion);
                    sa.setAlumno(alumnoOpt.get());
                    sa.setEstado(true);
                    seccionAlumnoRepository.save(sa);
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getDniAlum(),
                alumno.getNombres(),
                alumno.getApellidos()
        );
    }
}