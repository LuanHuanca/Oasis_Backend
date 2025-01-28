package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

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

    // Constructor por defecto
    public AdminPermiso() {}

    // Constructor que acepta un Integer
    public AdminPermiso(Admin admin, Permiso permiso) {
        this.admin = admin;
        this.permiso = permiso;
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
}