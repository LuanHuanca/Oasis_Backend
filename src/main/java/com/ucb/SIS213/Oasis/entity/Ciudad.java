package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Ciudad")
public class Ciudad {

    /*
    -- Table: Ciudad
    CREATE TABLE Ciudad (
        idCiudad serial  NOT NULL,
        ciudad varchar(45)  NOT NULL,
        Pais_idPais int  NOT NULL,
        CONSTRAINT Ciudad_pk PRIMARY KEY (idCiudad)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idciudad")
    private Long idCiudad;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "pais_idpais")
    private Long idPais;

    // Getters y Setters

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

}
