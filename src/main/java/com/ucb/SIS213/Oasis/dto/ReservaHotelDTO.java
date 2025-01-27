package com.ucb.SIS213.Oasis.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReservaHotelDTO {
    private Long idReservaHotel;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal precio;
    private int personas;
    private int idHotel;
    private String nroReservaHotel;
    private int habitaciones;

    private String detalle;
    private String hotel;
    private String ciudad;
    private String pais;

    // Constructores, getters y setters


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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
