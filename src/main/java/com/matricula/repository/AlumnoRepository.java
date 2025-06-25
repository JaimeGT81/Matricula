package com.matricula.repository;

import com.matricula.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlumnoRepository extends BaseRepository<Alumno, String> {
    @Query("SELECT a FROM Alumno a WHERE a.dniAlum NOT IN (SELECT sa.alumno.dniAlum FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.estado = true)")
    List<Alumno> findAlumnosNotInSeccion(@Param("nrc") String nrc);

    @Query("SELECT a FROM Alumno a WHERE a.dniAlum IN (SELECT sa.alumno.dniAlum FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.estado = true)")
    List<Alumno> findAlumnosBySeccion(@Param("nrc") String nrc);

    Page<Alumno> findByEstadoTrue(Pageable pageable);
}