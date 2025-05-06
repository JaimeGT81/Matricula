package com.matricula.entity;

import jakarta.persistence.*;
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
    private LocalDateTime fechaNacimiento;

    @Column(nullable = false)
    private Long celularDoc;

    @ManyToOne
    @JoinColumn(name = "idUbigeo")
    private Ubigeo ubigeo;

    @Column(length = 10)
    private String idDepartamento;

    @Column(length = 10)
    private String idProvincia;

    @Column(length = 10)
    private String idDistrito;

    @Column(nullable = false)
    private Long codCarrera;

    @Column(length = 200)
    private String direccionDomicilio;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(length = 50, nullable = false)
    private String usuarioRegistro;

    @Column(nullable = false)
    private LocalDateTime fechaUltModificacion;

    @Column(length = 50, nullable = false)
    private String usuarioUltModificacion;

    @Column(nullable = false)
    private Boolean estadoDoc;

    // Getters and setters...
}