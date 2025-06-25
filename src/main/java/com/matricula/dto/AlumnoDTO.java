package com.matricula.dto;

public class AlumnoDTO {
    private String dniAlum;
    private String nombres;
    private String apellidos;

    public AlumnoDTO(String dniAlum, String nombres, String apellidos) {
        this.dniAlum = dniAlum;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    // Getters and setters
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
}