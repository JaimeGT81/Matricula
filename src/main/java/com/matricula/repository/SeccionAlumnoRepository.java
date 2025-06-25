package com.matricula.repository;

import com.matricula.entity.SeccionAlumno;
import com.matricula.entity.Seccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SeccionAlumnoRepository extends BaseRepository<SeccionAlumno,Long> {
    @Query("SELECT sa FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.estado = true")
    List<SeccionAlumno> findBySeccion_SeccionNRC(@Param("nrc") String nrc);

    @Query("SELECT CASE WHEN COUNT(sa) > 0 THEN true ELSE false END FROM SeccionAlumno sa " +
            "WHERE sa.seccion.seccionNRC = :nrc AND sa.alumno.dniAlum = :dniAlumno AND sa.estado = true")
    void deleteBySeccion(Seccion seccion);
    @Query("SELECT COUNT(sa) > 0 FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.alumno.dniAlum = :dniAlum")
    boolean existsBySeccionAndAlumno(@Param("nrc") String nrc, @Param("dniAlum") String dniAlum);
    @Query("SELECT sa FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.alumno.dniAlum = :dniAlum")
    SeccionAlumno findBySeccionAndAlumno(@Param("nrc") String nrc, @Param("dniAlum") String dniAlum);
    void deleteBySeccion_SeccionNRC(String nrc);
    @Query("SELECT COUNT(sa) FROM SeccionAlumno sa WHERE sa.seccion.seccionNRC = :nrc AND sa.estado = true")
    int countParticipantesBySeccion(@Param("nrc") String nrc);

}
