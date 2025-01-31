package com.ucb.SIS213.Oasis.dto;

public class PermisoDTO {
    private Long idPermiso;
    private String permiso;

    public PermisoDTO(Long idPermiso, String permiso) {
        this.idPermiso = idPermiso;
        this.permiso = permiso;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }
    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }
    public String getPermiso() {
        return permiso;
    }
    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    // Getters and setters
}