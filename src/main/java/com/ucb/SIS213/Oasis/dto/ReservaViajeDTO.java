package com.ucb.SIS213.Oasis.dto;


import jakarta.persistence.Column;

import java.util.Date;

public class ReservaViajeDTO {

    private Long idReservaViaje;
    private Date fecha;
    private Long idCliente;
    private Long idViaje;
    private Long idSeguro;
    private Long idAlquiler;
    private Long idAtraccion;
    private Long idActividad;
    private Long idReservaHotel;
    private String ciudadOrigen;
    private String paisOrigen;
    private String ciudadDestino;
    private String paisDestino;
    private String nombrePersona;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoPersona;
    private Date fechaInicioViaje;
    private Date fechaFinViaje;

    // Getters y setters


    public Long getIdReservaViaje() {
        return idReservaViaje;
    }

    public void setIdReservaViaje(Long idReservaViaje) {
        this.idReservaViaje = idReservaViaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public Long getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Long idSeguro) {
        this.idSeguro = idSeguro;
    }

    public Long getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Long idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Long getIdAtraccion() {
        return idAtraccion;
    }

    public void setIdAtraccion(Long idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Long getIdReservaHotel() {
        return idReservaHotel;
    }

    public void setIdReservaHotel(Long idReservaHotel) {
        this.idReservaHotel = idReservaHotel;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoPersona() {
        return correoPersona;
    }

    public void setCorreoPersona(String correoPersona) {
        this.correoPersona = correoPersona;
    }

    public Date getFechaInicioViaje() {
        return fechaInicioViaje;
    }

    public void setFechaInicioViaje(Date fechaInicioViaje) {
        this.fechaInicioViaje = fechaInicioViaje;
    }

    public Date getFechaFinViaje() {
        return fechaFinViaje;
    }

    public void setFechaFinViaje(Date fechaFinViaje) {
        this.fechaFinViaje = fechaFinViaje;
    }
}