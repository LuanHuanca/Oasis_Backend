package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adminpermiso")
public class AdminPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadminpermiso")
    private Integer idAdminPermiso;

    @ManyToOne
    @JoinColumn(name = "admin_idadmin")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "permiso_idpermiso")
    private Permiso permiso;

    @Column(name = "tipopermiso")
    private String tipoPermiso;

    @Column(name = "fechaasignacion")
    private LocalDateTime fechaAsignacion;

    @Column(name = "activo")
    private Boolean activo;

    // Constructor por defecto
    public AdminPermiso() {}

    // Constructor que acepta un Integer
    public AdminPermiso(Admin admin, Permiso permiso) {
        this.admin = admin;
        this.permiso = permiso;
        this.tipoPermiso = "ADICIONAL";
        this.fechaAsignacion = LocalDateTime.now();
        this.activo = true;
    }

    // Constructor con tipo de permiso
    public AdminPermiso(Admin admin, Permiso permiso, String tipoPermiso) {
        this.admin = admin;
        this.permiso = permiso;
        this.tipoPermiso = tipoPermiso;
        this.fechaAsignacion = LocalDateTime.now();
        this.activo = true;
    }

    // Getters and setters
    public Integer getIdAdminPermiso() {
        return idAdminPermiso;
    }

    public void setIdAdminPermiso(Integer idAdminPermiso) {
        this.idAdminPermiso = idAdminPermiso;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "AdminPermiso{" +
                "idAdminPermiso=" + idAdminPermiso +
                ", admin=" + admin +
                ", permiso=" + permiso +
                ", tipoPermiso='" + tipoPermiso + '\'' +
                ", fechaAsignacion=" + fechaAsignacion +
                ", activo=" + activo +
                '}';
    }
}