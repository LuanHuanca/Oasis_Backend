package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permisotemporal")
public class PermisoTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermisotemporal")
    private Long idPermisoTemporal;

    @ManyToOne
    @JoinColumn(name = "admin_idadmin")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "permiso_idpermiso")
    private Permiso permiso;

    @Column(name = "fechainicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fechafin")
    private LocalDateTime fechaFin;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    // Constructores
    public PermisoTemporal() {}

    public PermisoTemporal(Admin admin, Permiso permiso, LocalDateTime fechaFin, String motivo) {
        this.admin = admin;
        this.permiso = permiso;
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = fechaFin;
        this.motivo = motivo;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getIdPermisoTemporal() {
        return idPermisoTemporal;
    }

    public void setIdPermisoTemporal(Long idPermisoTemporal) {
        this.idPermisoTemporal = idPermisoTemporal;
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

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Método para verificar si el permiso está activo y no ha expirado
    public boolean isValido() {
        LocalDateTime ahora = LocalDateTime.now();
        return activo && ahora.isAfter(fechaInicio) && ahora.isBefore(fechaFin);
    }

    // Método para verificar si el permiso ha expirado
    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(fechaFin);
    }

    @Override
    public String toString() {
        return "PermisoTemporal{" +
                "idPermisoTemporal=" + idPermisoTemporal +
                ", admin=" + admin +
                ", permiso=" + permiso +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", motivo='" + motivo + '\'' +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
