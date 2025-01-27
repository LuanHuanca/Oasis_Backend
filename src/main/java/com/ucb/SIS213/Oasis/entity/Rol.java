package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rol") // Asegúrate de que el nombre de la tabla sea correcto
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol") // Asegúrate de que el nombre de la columna sea correcto
    private Long idRol;

    @Column(name = "rol") // Asegúrate de que el nombre de la columna sea correcto
    private String rol;

    // Getters and setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}