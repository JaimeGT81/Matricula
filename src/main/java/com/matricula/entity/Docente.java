package com.matricula.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * HU005: Gestión de docentes (CRUD)
 */
@Entity
@Table(name = "Docente")
public class Docente {

    @Id
    @Column(length = 20)
    private String dniDocente;

    @Lob
    private String fotoDocente;

    @Column(length = 100, nullable = false)
    private String nombreDoc;

    @Column(length = 100, nullable = false)
    private String apellidoDoc;

    @Column(length = 10, nullable = false)
    private String generoDoc;

    @Column(nullable = false)
    private LocalDate fechaNacimiento; // MODIFICADO: Tipo de dato cambiado a LocalDate. Razón: La hora de nacimiento no es necesaria

    @Column(nullable = false)
    private Long celularDoc;

    @ManyToOne
    @JoinColumn(name = "idUbigeo")
    private Ubigeo ubigeo; // MODIFICADO: idDepartamento, idProvincia y idDistrito eliminados. Razón: Redundancia, la entidad Ubigeo ya encapsula esta información.

    @ManyToOne
    @JoinColumn(name = "idCarrera")
    private Carrera carrera; // MODIFICADO: Tipo de dato cambiado por la entidad Carrera

    @Column(length = 200)
    private String direccionDomicilio;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(length = 50, nullable = false)
    private String usuarioRegistro;

    private LocalDateTime fechaUltModificacion; // MODIFICADO: Atributo 'nullable' eliminado. Razón: Este campo solo se asigna al realizar una modificación del registro, es nulo en la creación inicial.

    @Column(length = 50)
    private String usuarioUltModificacion; // MODIFICADO: Atributo 'nullable' eliminado. Razón: Este campo solo se asigna al realizar una modificación del registro, es nulo en la creación inicial.

    @Column(nullable = false)
    private Boolean estadoDoc;

    public String getDniDocente() {
        return dniDocente;
    }

    public void setDniDocente(String dniDocente) {
        this.dniDocente = dniDocente;
    }

    public String getFotoDocente() {
        return fotoDocente;
    }

    public void setFotoDocente(String fotoDocente) {
        this.fotoDocente = fotoDocente;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getApellidoDoc() {
        return apellidoDoc;
    }

    public void setApellidoDoc(String apellidoDoc) {
        this.apellidoDoc = apellidoDoc;
    }

    public String getGeneroDoc() {
        return generoDoc;
    }

    public void setGeneroDoc(String generoDoc) {
        this.generoDoc = generoDoc;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getCelularDoc() {
        return celularDoc;
    }

    public void setCelularDoc(Long celularDoc) {
        this.celularDoc = celularDoc;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }

    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
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

    public Boolean getEstadoDoc() {
        return estadoDoc;
    }

    public void setEstadoDoc(Boolean estadoDoc) {
        this.estadoDoc = estadoDoc;
    }
}