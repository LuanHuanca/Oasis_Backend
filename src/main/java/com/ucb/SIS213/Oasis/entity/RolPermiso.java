package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rolpermiso") // Asegúrate de que el nombre de la tabla sea correcto y en minúsculas
public class RolPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrolpermiso") // Asegúrate de que el nombre de la columna sea correcto y en minúsculas
    private Long idRolPermiso;

    @ManyToOne
    @JoinColumn(name = "rol_idrol") // Asegúrate de que el nombre de la columna sea correcto y en minúsculas
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_idpermiso") // Asegúrate de que el nombre de la columna sea correcto y en minúsculas
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