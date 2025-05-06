package com.matricula.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * HU003: Gestión de secciones (CRUD)
 */
@Entity
@Table(name = "Seccion")
public class Seccion {

    @Id
    @Column(length = 20)
    private String seccionNRC;

    @ManyToOne
    @JoinColumn(name = "codCurso", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "dniDocente", nullable = false)
    private Docente docente;

    @Column(name = "Id_AulaDR", length = 50, nullable = false)
    private String idAulaDR;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(length = 20, nullable = false)
    private String diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(length = 50, nullable = false)
    private String usuarioRegistro;

    @Column(nullable = false)
    private LocalDateTime fechaUltModificacion;

    @Column(length = 50, nullable = false)
    private String usuarioUltModificacion;

    @Column(nullable = false)
    private Boolean estadoSeccion;

    @Column(length = 50, nullable = false)
    private String modalidad;

    // Getters and setters...
}
