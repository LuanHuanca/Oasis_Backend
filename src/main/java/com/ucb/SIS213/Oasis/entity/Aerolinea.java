package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Aerolinea")
public class Aerolinea {

    /*
    -- Table: Aerolinea
    CREATE TABLE Aerolinea (
       idAerolinea serial  NOT NULL,
       aerolinea varchar(45)  NOT NULL,
       CONSTRAINT Aerolinea_pk PRIMARY KEY (idAerolinea)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaerolinea")
    private Long idAerolinea;

    @Column(name = "aerolinea")
    private String aerolinea;

    // Getters y Setters

    public Long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    @Override
    public String toString() {
        return "Aerolinea{" +
                "idAerolinea=" + idAerolinea +
                ", aerolinea='" + aerolinea + '\'' +
                '}';
    }
}
