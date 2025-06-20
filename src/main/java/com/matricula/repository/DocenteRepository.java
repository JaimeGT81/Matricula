package com.matricula.repository;

import com.matricula.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* JPA para la entidad Docente */
@Repository
public interface DocenteRepository extends BaseRepository<Docente, String> {
    Page<Docente> findByDniDocenteStartingWithIgnoreCaseAndNombreDocStartingWithIgnoreCaseAndApellidoDocStartingWithIgnoreCase(
            String dni, String nombre, String apellido, Pageable pageable
    );
    Page<Docente> findByDniDocenteStartingWithIgnoreCaseAndNombreDocStartingWithIgnoreCaseAndApellidoDocStartingWithIgnoreCaseAndEstadoTrue(
            String dni, String nombre, String apellido, Pageable pageable
    );
}