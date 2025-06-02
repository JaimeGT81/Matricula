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
    private int creditos; // MODIFICADO: Atributo 'creditos' agregado. Razón: Para almacenar la cantidad de créditos del curso.

    // getters y setters...

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public Short getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Short codCarrera) {
        this.codCarrera = codCarrera;
    }

    public String getNomCarrera() {
        return nomCarrera;
    }

    public void setNomCarrera(String nomCarrera) {
        this.nomCarrera = nomCarrera;
    }

    public String getNomCurso() {
        return nomCurso;
    }

    public void setNomCurso(String nomCurso) {
        this.nomCurso = nomCurso;
    }

    public Short getCiclo() {
        return ciclo;
    }

    public void setCiclo(Short ciclo) {
        this.ciclo = ciclo;
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

    public Boolean getEstadoCurso() {
        return estadoCurso;
    }

    public void setEstadoCurso(Boolean estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
}