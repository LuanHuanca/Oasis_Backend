package com.ucb.SIS213.Oasis.dto;



public class AtraccionDTO {

    private Long idAtraccion;
    private String atraccion;
    private Long idCatAtrac;
    private Long idCiudad;
    private Double precio;
    private String detalle;
    private String categoria;
    private String ciudad;
    private String pais;

    // Getters y setters


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
