package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rolpermiso")
public class RolPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrolpermiso")
    private Integer idRolPermiso;

    @ManyToOne
    @JoinColumn(name = "rol_idrol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_idpermiso")
    private Permiso permiso;

    // Constructor por defecto
    public RolPermiso() {}

    // Constructor con parámetros
    public RolPermiso(Rol rol, Permiso permiso) {
        this.rol = rol;
        this.permiso = permiso;
    }

    // Getters y setters
    public Integer getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Integer idRolPermiso) {
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