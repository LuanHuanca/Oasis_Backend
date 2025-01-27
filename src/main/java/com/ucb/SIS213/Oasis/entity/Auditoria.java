package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Auditoria")
public class Auditoria {

    /*
    -- Table: Auditoria
CREATE TABLE Auditoria (
                           idAudit serial  NOT NULL,
                           actividad varchar(250)  NOT NULL,
                           fecha date  NOT NULL,
                           hora time  NOT NULL,
                           fechaInicio timestamp,
                           fechaFin timestamp,
                           ip varchar(50),
                           admin_idAdmin int,
                           Cliente_idCliente int,
                           CONSTRAINT Auditoria_pk PRIMARY KEY (idAudit)
);

     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaudit")
    private Long idAudit;

    @Column(name = "actividad")
    private String actividad;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "fechainicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fechafin")
    private LocalDateTime fechaFin;

    @Column(name = "ip")
    private String ip;

    @Column(name = "admin_idadmin")
    private Long idAdmin;

    @Column(name = "Cliente_idcliente")
    private Long idCliente;


    // Getters y Setters

    public Long getIdAudit() {
        return idAudit;
    }

    public void setIdAudit(Long idAudit) {
        this.idAudit = idAudit;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getIdAdmin() {
        // O cualquier otro valor predeterminado que desees
        return Objects.requireNonNullElse(idAdmin, -1L);
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdCliente() {
        return Objects.requireNonNullElse(idCliente, -1L);
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    // ToString

    @Override
    public String toString() {
        return "Auditoria{" +
                "idAudit=" + idAudit +
                ", actividad='" + actividad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", idAdmin=" + idAdmin +
                ", idCliente=" + idCliente +
                '}';
    }
}
