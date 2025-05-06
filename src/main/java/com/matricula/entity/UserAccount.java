package com.matricula.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * HU001: Módulo de autenticación
 */
@Entity
@Table(name = "UserAccount")
public class UserAccount {
    @Id
    @Column(length = 50, name = "User_id")
    private String userId;

    @Column(length = 100, name = "User_email", nullable = false)
    private String userEmail;

    @Column(length = 100, name = "User_password", nullable = false)
    private String userPassword;

    @Column(name = "Fecha_Registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "Fecha_Conexion")
    private LocalDateTime fechaConexion;

    @Column(name = "Rol")
    private Short rol;

    // getters y setters...
    public Short getRol() {
        return rol;
    }

    public void setRol(Short rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaConexion() {
        return fechaConexion;
    }

    public void setFechaConexion(LocalDateTime fechaConexion) {
        this.fechaConexion = fechaConexion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}