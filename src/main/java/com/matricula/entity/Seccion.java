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

    public String getSeccionNRC() {
        return seccionNRC;
    }

    public void setSeccionNRC(String seccionNRC) {
        this.seccionNRC = seccionNRC;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public String getIdAulaDR() {
        return idAulaDR;
    }

    public void setIdAulaDR(String idAulaDR) {
        this.idAulaDR = idAulaDR;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public LocalDateTime getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(LocalDateTime fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public String getUsuarioUltModificacion() {
        return usuarioUltModificacion;
    }

    public void setUsuarioUltModificacion(String usuarioUltModificacion) {
        this.usuarioUltModificacion = usuarioUltModificacion;
    }

    public Boolean getEstadoSeccion() {
        return estadoSeccion;
    }

    public void setEstadoSeccion(Boolean estadoSeccion) {
        this.estadoSeccion = estadoSeccion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
