package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Vuelo")
public class Vuelo {

    /*
    -- Table: Vuelo
CREATE TABLE Vuelo (
       idViaje serial  NOT NULL,
       origen int  NOT NULL,
       destino int  NOT NULL,
       Aerolinea_idAerolinea int  NOT NULL,
       fechaInicio date  NOT NULL,
       fechaFin date  NOT NULL,
       precio decimal(7,2)  NOT NULL,
       personas int  NOT NULL,
       nroReserva varchar(45)  NOT NULL,
       CONSTRAINT Vuelo_pk PRIMARY KEY (idViaje)
);
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idviaje")
    private Long idViaje;

    @Column(name = "origen")
    private Long origen;

    @Column(name = "destino")
    private Long destino;

    @Column(name = "aerolinea_idaerolinea")
    private Long idAerolinea;

    @Column(name = "fechainicio")
    private Date fechaInicio;

    @Column(name = "fechafin")
    private Date fechaFin;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "personas")
    private Long personas;

    @Column(name = "nroreserva")
    private String nroReserva;

    // Getters y Setters

    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public Long getOrigen() {
        return origen;
    }

    public void setOrigen(Long origen) {
        this.origen = origen;
    }

    public Long getDestino() {
        return destino;
    }

    public void setDestino(Long destino) {
        this.destino = destino;
    }

    public Long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getPersonas() {
        return personas;
    }

    public void setPersonas(Long personas) {
        this.personas = personas;
    }

    public String getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(String nroReserva) {
        this.nroReserva = nroReserva;
    }

    // ToString

    @Override
    public String toString() {
        return "Vuelo{" +
                "idViaje=" + idViaje +
                ", origen=" + origen +
                ", destino=" + destino +
                ", idAerolinea=" + idAerolinea +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", precio=" + precio +
                ", personas=" + personas +
                ", nroReserva='" + nroReserva + '\'' +
                '}';
    }

}
