package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
public class Admin {

    /*
    -- Table: admin
    CREATE TABLE admin (
        idAdmin serial  NOT NULL,
        correo varchar(45)  NOT NULL,
        password varchar(255)  NOT NULL,
        rol varchar(45)  NOT NULL,
        Persona_idPersona int  NOT NULL,
        CONSTRAINT admin_pk PRIMARY KEY (idAdmin)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadmin")
    private Long idAdmin;

    @Column(name = "correo")
    private String correo;

    @Column(name = "persona_idpersona")
    private Long idPersona;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_idrol")
    private Rol rol;

    @Column(name = "estadocuenta")
    private Boolean estadoCuenta;

    @Column(name = "intentosfallidos")
    private Integer intentosFallidos;

    @Column(name = "fechabloqueo")
    private LocalDateTime fechaBloqueo;

    @Column(name = "motivobloqueo")
    private String motivoBloqueo;

    // Getters and setters
    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Boolean estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
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

    // toString

    @Override
    public String toString() {
        return "admin{" + "idAdmin=" + idAdmin +
                ", correo=" + correo +
                ", password=" + password +
                ", rol=" + rol +
                ", idPersona=" + idPersona + '}';
    }


}
