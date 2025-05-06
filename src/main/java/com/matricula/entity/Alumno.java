package com.matricula.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * HU002: Gestión de alumnos (CRUD)
 */

@Entity
@Table(name = "Alumno")
public class Alumno {
    @Id
    @Column(length = 20)
    private String dniAlum;

    @Lob
    private String foto;

    @Column(length = 100, nullable = false)
    private String nombreAlum;

    @Column(length = 100, nullable = false)
    private String apellidolAlum;

    @Column(length = 10, nullable = false)
    private String generoAlum;

    @ManyToOne
    @JoinColumn(name = "idUbigeo")
    private Ubigeo ubigeo;

    private Long codCarrera;
    private LocalDateTime fechaRegistro;
    private String usuarioRegistro;
    private LocalDateTime fechaUltModificacion;
    private String usuarioUltModificacion;
    private Boolean estadoAlum;

    // getters y setters...

    public String getDniAlum() {
        return dniAlum;
    }

    public void setDniAlum(String dniAlum) {
        this.dniAlum = dniAlum;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombreAlum() {
        return nombreAlum;
    }

    public void setNombreAlum(String nombreAlum) {
        this.nombreAlum = nombreAlum;
    }

    public String getApellidolAlum() {
        return apellidolAlum;
    }

    public void setApellidolAlum(String apellidolAlum) {
        this.apellidolAlum = apellidolAlum;
    }

    public String getGeneroAlum() {
        return generoAlum;
    }

    public void setGeneroAlum(String generoAlum) {
        this.generoAlum = generoAlum;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
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

    public Boolean getEstadoAlum() {
        return estadoAlum;
    }

    public void setEstadoAlum(Boolean estadoAlum) {
        this.estadoAlum = estadoAlum;
    }
}
