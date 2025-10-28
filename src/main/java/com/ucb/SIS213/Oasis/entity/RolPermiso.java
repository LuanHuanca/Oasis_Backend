package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rolpermiso")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrolpermiso")
    private Long idRolPermiso;

    @ManyToOne
    @JoinColumn(name = "rol_idrol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_idpermiso")
    private Permiso permiso;

    @Column(name = "fechaasignacion")
    private LocalDateTime fechaAsignacion;

    // Constructores
    public RolPermiso() {}

    public RolPermiso(Rol rol, Permiso permiso) {
        this.rol = rol;
        this.permiso = permiso;
        this.fechaAsignacion = LocalDateTime.now();
    }

    // Getters y Setters
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

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    @Override
    public String toString() {
        return "RolPermiso{" +
                "idRolPermiso=" + idRolPermiso +
                ", rol=" + rol +
                ", permiso=" + permiso +
                ", fechaAsignacion=" + fechaAsignacion +
                '}';
    }
}
