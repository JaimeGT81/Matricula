package com.matricula.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Cuota")
public class Cuota extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id", updatable = false, nullable = false, length = 36)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dniAlum")
    private Alumno alumno;

    /** 0 = matrícula, 1..N cuotas mensuales */
    private int numeroCuota;

    private BigDecimal monto;

    private LocalDateTime fechaGeneracion;

    /** Esta flag no es soft-delete, sino “ya pagada” */
    private Boolean pagado = false;

    // getters / setters


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
