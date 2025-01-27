package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Auto")
public class Auto {

    /*
    -- Table: Auto
    CREATE TABLE Auto (
      idAuto serial  NOT NULL,
      modelo varchar(45)  NOT NULL,
      marca varchar(45)  NOT NULL,
      tipo varchar(45)  NOT NULL,
      CONSTRAINT Auto_pk PRIMARY KEY (idAuto)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idauto")
    private Long idAuto;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "tipo")
    private String tipo;

    // Getters y Setters


    public Long getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Long idAuto) {
        this.idAuto = idAuto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
