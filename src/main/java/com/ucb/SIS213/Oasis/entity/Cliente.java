package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cliente")
public class Cliente {

    /*
    CREATE TABLE Cliente (
    idCliente SERIAL  NOT NULL,
    correo varchar(45)  NOT NULL,
    password varchar(45)  NOT NULL,
    estadoCuenta varchar(45)  NOT NULL,
    Persona_idPersona int  NOT NULL,
    CONSTRAINT Cliente_pk PRIMARY KEY (idCliente)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long idCliente;

    @Column(name = "correo")
    private String correo;

    @Column(name = "password")
    private String password;

    @Column(name = "estadocuenta")
    private Boolean estadoCuenta;

    @Column(name = "intentosfallidos")
    private Integer intentosFallidos;

    @Column(name = "fechabloqueo")
    private LocalDateTime fechaBloqueo;

    @Column(name = "motivobloqueo")
    private String motivoBloqueo;

    @Column(name = "Persona_idpersona")
    private Long idPersona;

    // Getters y Setters

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Boolean estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(Integer intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public LocalDateTime getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(LocalDateTime fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public String getMotivoBloqueo() {
        return motivoBloqueo;
    }

    public void setMotivoBloqueo(String motivoBloqueo) {
        this.motivoBloqueo = motivoBloqueo;
    }

    // ToString

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", estadoCuenta=" + estadoCuenta +
                ", persona=" + idPersona +
                '}';
    }


    
}
