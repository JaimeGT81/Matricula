package com.matricula.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "alumno")
public class Alumno extends BaseEntity {

    @Id
    @Column(name = "dni", length = 20)
    private String dniAlum;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "genero", nullable = false, length = 10)
    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubigeo", nullable = false)
    private Ubigeo ubigeo;

    @Column(name = "cod_carrera")
    private Long codigoCarrera;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "usuario_registro", updatable = false)
    private String usuarioRegistro;

    @Column(name = "fecha_ult_modificacion")
    private LocalDateTime fechaUltModificacion;

    @Column(name = "usuario_ult_modificacion")
    private String usuarioUltModificacion;

    @Column(name = "activo")
    private Boolean activo;

    public Alumno() { }

    public String getDniAlum() {
        return dniAlum;
    }

    public void setDniAlum(String dniAlum) {
        this.dniAlum = dniAlum;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public Long getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(Long codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
