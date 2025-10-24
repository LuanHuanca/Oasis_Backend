package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RolPermiso")
public class RolPermiso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRolPermiso")
    private Integer idRolPermiso;

    @ManyToOne
    @JoinColumn(name = "rol_idRol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_idPermiso", nullable = false)
    private Permiso permiso;

    public Integer getIdRolPermiso() { return idRolPermiso; }
    public void setIdRolPermiso(Integer idRolPermiso) { this.idRolPermiso = idRolPermiso; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Permiso getPermiso() { return permiso; }
    public void setPermiso(Permiso permiso) { this.permiso = permiso; }
}
