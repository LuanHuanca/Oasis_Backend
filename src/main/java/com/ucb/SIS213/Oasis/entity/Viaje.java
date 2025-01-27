package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservaviaje")
public class Viaje {

    /*
    -- Table: ReservaViaje
    CREATE TABLE ReservaViaje (
          idReservaViaja serial  NOT NULL,
          fecha date  NOT NULL,
          Cliente_idCliente int  NOT NULL,
          Viaje_idViaje int  NOT NULL,
          Seguro_idSeguro int  NOT NULL,
          AlquilerAuto_idAlquiler int  NOT NULL,
          Atraccion_idAtraccion int  NOT NULL,
          Actividad_idActividad int  NOT NULL,
          ReservaHotel_idReservaHotel int  NOT NULL,
          CONSTRAINT ReservaViaje_pk PRIMARY KEY (idReservaViaja)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreservaviaja")
    private Long idReservaViaje;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cliente_idcliente")
    private Long idCliente;

    @Column(name = "Viaje_idviaje")
    private Long idViaje;

    @Column(name = "Seguro_idseguro")
    private Long idSeguro;

    @Column(name = "Alquilerauto_idalquiler")
    private Long idAlquiler;

    @Column(name = "Atraccion_idatraccion")
    private Long idAtraccion;

    @Column(name = "Actividad_idactividad")
    private Long idActividad;

    @Column(name = "Reservahotel_idreservahotel")
    private Long idReservaHotel;

    // Getters y Setters

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

    public Long getidCliente() {
        return idCliente;
    }

    public void setidCliente(Long Cliente_idCliente) {
        this.idCliente = Cliente_idCliente;
    }

    public Long getidViaje() {
        return idViaje;
    }

    public void setidViaje(Long Viaje_idViaje) {
        this.idViaje = Viaje_idViaje;
    }

    public Long getidSeguro() {
        return idSeguro;
    }

    public void setidSeguro(Long Seguro_idSeguro) {
        this.idSeguro = Seguro_idSeguro;
    }

    public Long getidAlquiler() {
        return idAlquiler;
    }

    public void setidAlquiler(Long AlquilerAuto_idAlquiler) {
        this.idAlquiler = AlquilerAuto_idAlquiler;
    }

    public Long getidAtraccion() {
        return idAtraccion;
    }

    public void setidAtraccion(Long Atraccion_idAtraccion) {
        this.idAtraccion = Atraccion_idAtraccion;
    }

    public Long getidActividad() {
        return idActividad;
    }

    public void setidActividad(Long Actividad_idActividad) {
        this.idActividad = Actividad_idActividad;
    }

    public Long getidReservaHotel() {
        return idReservaHotel;
    }

    public void setidReservaHotel(Long ReservaHotel_idReservaHotel) {
        this.idReservaHotel = ReservaHotel_idReservaHotel;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "idReservaViaje=" + idReservaViaje +
                ", fecha=" + fecha +
                ", Cliente_idCliente=" + idCliente +
                ", Viaje_idViaje=" + idViaje +
                ", Seguro_idSeguro=" + idSeguro +
                ", AlquilerAuto_idAlquiler=" + idAlquiler +
                ", Atraccion_idAtraccion=" + idAtraccion +
                ", Actividad_idActividad=" + idActividad +
                ", ReservaHotel_idReservaHotel=" + idReservaHotel +
                '}';
    }

}
