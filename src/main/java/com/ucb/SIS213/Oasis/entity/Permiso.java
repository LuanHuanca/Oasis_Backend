package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permiso") // Asegúrate de que el nombre de la tabla sea correcto y en minúsculas
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermiso") // Asegúrate de que el nombre de la columna sea correcto y en minúsculas
    private Long idPermiso;

    @Column(name = "permiso") // Asegúrate de que el nombre de la columna sea correcto y en minúsculas
    private String permiso;

    // Getters and setters
    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
}