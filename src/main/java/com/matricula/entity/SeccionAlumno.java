package com.matricula.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SeccionAlumno")
public class SeccionAlumno extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seccionNRC", nullable = false)
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "dniAlumno", nullable = false)
    private Alumno alumno;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(length = 50, nullable = false)
    private String usuarioRegistro;

    @Column
    private LocalDateTime fechaUltModificacion;

    @Column(length = 50)
    private String usuarioUltModificacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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
}
