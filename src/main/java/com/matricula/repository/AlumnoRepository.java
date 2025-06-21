package com.matricula.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.matricula.entity.Alumno;

public interface AlumnoRepository extends BaseRepository<Alumno, String> {

    Page<Alumno> findByDniAlumStartingWithIgnoreCaseAndNombresStartingWithIgnoreCaseAndApellidosStartingWithIgnoreCase(
            String dni, String nombres, String apellidos, Pageable pageable
    );

}
