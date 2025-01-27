package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "actividad")
public class Actividad {

    /*
    CREATE TABLE Actividad (
       idActividad serial  NOT NULL,
       actividad varchar(45)  NOT NULL,
       Ciudad_idCiudad int  NOT NULL,
       CategoriaActividad_idCatActi int  NOT NULL,
       fecha date  NOT NULL,
       precio decimal(7,2)  NOT NULL,
       Detalle varchar(255)  NOT NULL,
       CONSTRAINT Actividad_pk PRIMARY KEY (idActividad)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idactividad")
    private Long idActividad;

    @Column(name = "actividad")
    private String actividad;

    @Column(name = "ciudad_idciudad")
    private Long idCiudad;

    @Column(name = "categoriaactividad_idcatacti")
    private Long idCatActi;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "detalle")
    private String detalle;

    // Getters y Setters

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getIdCatActi() {
        return idCatActi;
    }

    public void setIdCatActi(Long idCatActi) {
        this.idCatActi = idCatActi;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    // ToString

    @Override
    public String toString() {
        return "Actividad{" +
                "idActividad=" + idActividad +
                ", actividad='" + actividad + '\'' +
                ", idCiudad=" + idCiudad +
                ", idCatActi=" + idCatActi +
                ", fecha='" + fecha + '\'' +
                ", precio=" + precio +
                ", detalle='" + detalle + '\'' +
                '}';
    }


}
