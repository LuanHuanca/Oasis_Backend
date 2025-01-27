package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
public class RolPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRolPermiso;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idPermiso")
    private Permiso permiso;

    // Getters and setters
    public Long getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Long idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}