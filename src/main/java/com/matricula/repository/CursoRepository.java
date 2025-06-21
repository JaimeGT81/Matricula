package com.matricula.repository;

import com.matricula.entity.Curso;
import com.matricula.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CursoRepository extends BaseRepository<Curso, String> {
    @Query("SELECT c FROM Curso c WHERE " +
            "(:codCurso = '' OR LOWER(c.codCurso) LIKE LOWER(CONCAT(:codCurso, '%'))) AND " +
            "(:nomCurso = '' OR LOWER(c.nomCurso) LIKE LOWER(CONCAT(:nomCurso, '%'))) AND " +
            "(:creditos = 0 OR c.creditos = :creditos)")
    Page<Curso> findByCriteria(
            @Param("codCurso") String codCurso,
            @Param("nomCurso") String nomCurso,
            @Param("creditos") Integer creditos,
            Pageable pageable
    );

}
