package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reservahotel")
public class ReservaHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreservahotel")
    private Long idReservaHotel;

    @Column(name = "fechainicio")
    private Date fechaInicio;

    @Column(name = "fechafin")
    private Date fechaFin;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "personas")
    private int personas;

    @Column(name = "hotel_idhotel")
    private int idHotel;

    @Column(name = "nroreservahotel")
    private String nroReservaHotel;

    @Column(name = "habitaciones")
    private int habitaciones;

    @Column(name = "detalle")
    private String detalle;

    // Getters and Setters

    public Long getIdReservaHotel() {
        return idReservaHotel;
    }

    public void setIdReservaHotel(Long idReservaHotel) {
        this.idReservaHotel = idReservaHotel;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNroReservaHotel() {
        return nroReservaHotel;
    }

    public void setNroReservaHotel(String nroReservaHotel) {
        this.nroReservaHotel = nroReservaHotel;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "ReservaHotel{" +
                "idReservaHotel=" + idReservaHotel +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precio=" + precio +
                ", personas=" + personas +
                ", idHotel=" + idHotel +
                ", nroReservaHotel='" + nroReservaHotel + '\'' +
                ", habitaciones=" + habitaciones +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
