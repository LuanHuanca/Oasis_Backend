package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "atraccion")
public class Atraccion {

    /*
    -- Table: Atraccion
    CREATE TABLE Atraccion (
       idAtraccion serial  NOT NULL,
       atraccion varchar(45)  NOT NULL,
       CategoriaAtraccion_idCatAtrac int  NOT NULL,
       Ciudad_idCiudad int  NOT NULL,
       precio decimal(7,2)  NOT NULL,
       detalle varchar(255)  NOT NULL,
       CONSTRAINT Atraccion_pk PRIMARY KEY (idAtraccion)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idatraccion")
    private Long idAtraccion;

    @Column(name = "atraccion")
    private String atraccion;

    @Column(name = "categoriaatraccion_idcatatrac")
    private Long idCatAtrac;

    @Column(name = "ciudad_idciudad")
    private Long idCiudad;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "detalle")
    private String detalle;

    // Getters y Setters

    public Long getIdAtraccion() {
        return idAtraccion;
    }

    public void setIdAtraccion(Long idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public String getAtraccion() {
        return atraccion;
    }

    public void setAtraccion(String atraccion) {
        this.atraccion = atraccion;
    }

    public Long getIdCatAtrac() {
        return idCatAtrac;
    }

    public void setIdCatAtrac(Long idCatAtrac) {
        this.idCatAtrac = idCatAtrac;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    // toString

    @Override
    public String toString() {
        return "Atraccion{" +
                "idAtraccion=" + idAtraccion +
                ", atraccion=" + atraccion +
                ", idCatAtrac=" + idCatAtrac +
                ", idCiudad=" + idCiudad +
                ", precio=" + precio +
                ", detalle=" + detalle +
                '}';
    }

}
