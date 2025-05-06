package com.matricula.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * HU004: Gestión de cursos (CRUD)
 */
@Entity
@Table(name = "Curso")
public class Curso {
    @Id
    @Column(length = 20)
    private String codCurso;

    private Short codCarrera;

    @Column(length = 100, nullable = false)
    private String nomCarrera;

    @Column(length = 100, nullable = false)
    private String nomCurso;

    private Short ciclo;
    private LocalDateTime fechaRegistro;
    private String usuarioRegistro;
    private LocalDateTime fechaUltModificacion;
    private String usuarioUltModificacion;
    private Boolean estadoCurso;

    // getters y setters...
}