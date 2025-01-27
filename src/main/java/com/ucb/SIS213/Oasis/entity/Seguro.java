package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seguro")
public class Seguro {

    /*
    -- Table: Seguro
    CREATE TABLE Seguro (
        idSeguro serial  NOT NULL,
        seguro varchar(45)  NOT NULL,
        cobertura varchar(45)  NOT NULL,
        precio decimal(7,2)  NOT NULL,
        CONSTRAINT Seguro_pk PRIMARY KEY (idSeguro)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseguro")
    private Long idSeguro;

    @Column(name = "seguro")
    private String seguro;

    @Column(name = "cobertura")
    private String cobertura;

    @Column(name = "precio")
    private Double precio;

    // Getters y Setters

    public Long getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Long idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Seguro{" +
                "idSeguro=" + idSeguro +
                ", seguro='" + seguro + '\'' +
                ", cobertura='" + cobertura + '\'' +
                ", precio=" + precio +
                '}';
    }


}
