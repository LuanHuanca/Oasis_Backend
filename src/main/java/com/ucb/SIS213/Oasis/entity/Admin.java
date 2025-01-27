package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

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

    @Column(name = "password")
    private String password;

    @Column(name = "rol")
    private String rol;

    @Column(name = "Persona_idpersona")
    private Long idPersona;

    // Getters y Setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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
