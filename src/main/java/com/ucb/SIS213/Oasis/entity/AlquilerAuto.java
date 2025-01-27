package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alquilerauto")
public class AlquilerAuto {

    /*
    -- Table: AlquilerAuto
    CREATE TABLE AlquilerAuto (
      idAlquiler serial  NOT NULL,
      precio decimal(7,2)  NOT NULL,
      dias int  NOT NULL,
      empresa varchar(65)  NOT NULL,
      Auto_idAuto int  NOT NULL,
      Ciudad_idCiudad int  NOT NULL,
      CONSTRAINT AlquilerAuto_pk PRIMARY KEY (idAlquiler)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalquiler")
    private Long idAlquiler;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "dias")
    private Integer dias;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "auto_idauto")
    private Long idAuto;

    @Column(name = "ciudad_idciudad")
    private Long idCiudad;

    // Getters y Setters

    public Long getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Long idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Long getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Long idAuto) {
        this.idAuto = idAuto;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

}
