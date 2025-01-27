package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Persona")
public class Persona {

    /*
    CREATE TABLE Persona (
    idPersona SERIAL  NOT NULL,
    Nombre varchar(45)  NOT NULL,
    ApellidoP varchar(45)  NOT NULL,
    ApellidoM varchar(45)  NOT NULL,
    Telefono varchar(45)  NOT NULL,
    CONSTRAINT Persona_pk PRIMARY KEY (idPersona)
);


     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idpersona")
    private Long idPersona;

    @Column (name = "Nombre")
    private String Nombre;

    @Column (name = "ApellidoP")
    private String ApellidoP;

    @Column (name = "ApellidoM")
    private String ApellidoM;

    @Column (name = "Telefono")
    private String Telefono;

    // Getters y Setters

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoP() {
        return ApellidoP;
    }

    public void setApellidoP(String ApellidoP) {
        this.ApellidoP = ApellidoP;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public void setApellidoM(String ApellidoM) {
        this.ApellidoM = ApellidoM;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    //To String

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", Nombre=" + Nombre +
                ", ApellidoP=" + ApellidoP +
                ", ApellidoM=" + ApellidoM +
                ", Telefono=" + Telefono + '}';
    }

}
