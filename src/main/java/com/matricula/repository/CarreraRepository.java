package com.matricula.repository;

import com.matricula.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* JPA para la entidad Carrera */
@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {
}