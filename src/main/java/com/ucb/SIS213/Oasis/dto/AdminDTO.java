package com.ucb.SIS213.Oasis.dto;

import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;

import java.util.List;

public class AdminDTO {
    private Long idPersona;
    private String correo;
    private String rol;
    private List<AdminPermiso> permisos;

    public AdminDTO(Admin admin, List<AdminPermiso> permisos) {
        this.idPersona = admin.getIdPersona();
        this.correo = admin.getCorreo();
        this.rol = admin.getRol().getRol(); // Extraer el valor de la propiedad rol del objeto Rol
        this.permisos = permisos;
    }

    // Getters and setters
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<AdminPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<AdminPermiso> permisos) {
        this.permisos = permisos;
    }
}