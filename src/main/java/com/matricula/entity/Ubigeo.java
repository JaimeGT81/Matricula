package com.matricula.entity;

import jakarta.persistence.*;

/**
 * HU006: Selector encadenado de Ubigeo
 */
@Entity
@Table(name = "Ubigeo")
public class Ubigeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbigeo;

    @Column(length = 10, nullable = false)
    private String idDepa;

    @Column(length = 10, nullable = false)
    private String idProv;

    @Column(length = 10, nullable = false)
    private String idDist;

    @Column(length = 100, nullable = false)
    private String departamento;

    @Column(length = 100, nullable = false)
    private String provincia;

    @Column(length = 100, nullable = false)
    private String distrito;

    // getters y setters...

    public Integer getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(Integer idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getIdDepa() {
        return idDepa;
    }

    public void setIdDepa(String idDepa) {
        this.idDepa = idDepa;
    }

    public String getIdProv() {
        return idProv;
    }

    public void setIdProv(String idProv) {
        this.idProv = idProv;
    }

    public String getIdDist() {
        return idDist;
    }

    public void setIdDist(String idDist) {
        this.idDist = idDist;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}