package com.matricula.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

/* DTO para los docentes */
public class DocenteDto {
    private String dni;
    private String nombre;
    private String apellido;
    private String genero;
    private LocalDate fechaNacimiento;
    private Long celular;
    private Integer ubigeo;
    private Integer carreraId;
    private String carreraNombre;
    private String direccion;
    private String fotoActual; /* Ruta de la foto actual */
    private MultipartFile fotoNueva; /* Permite obtener la foto del input file */
    private String usuarioRegistro;
    private LocalDateTime fechaRegistro;
    private String usuarioModficacion;
    private LocalDateTime fechaModificacion;
    private Boolean estado;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Integer getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Integer ubigeo) {
        this.ubigeo = ubigeo;
    }

    public Integer getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Integer carreraId) {
        this.carreraId = carreraId;
    }

    public String getCarreraNombre() {
        return carreraNombre;
    }

    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoActual() {
        return fotoActual;
    }

    public void setFotoActual(String fotoActual) {
        this.fotoActual = fotoActual;
    }

    public MultipartFile getFotoNueva() {
        return fotoNueva;
    }

    public void setFotoNueva(MultipartFile fotoNueva) {
        this.fotoNueva = fotoNueva;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioModficacion() {
        return usuarioModficacion;
    }

    public void setUsuarioModficacion(String usuarioModficacion) {
        this.usuarioModficacion = usuarioModficacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}