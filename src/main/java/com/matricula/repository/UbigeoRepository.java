package com.matricula.repository;

import com.matricula.entity.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Ubigeo.
 */
@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, Integer> {
}
