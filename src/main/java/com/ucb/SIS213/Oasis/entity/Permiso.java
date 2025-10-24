package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permiso")
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermiso")
    private Long idPermiso;

    @Column(name = "permiso", nullable = false, length = 100)
    private String permiso;

    @Column(name = "descripcion", length = 255) // si no existe en BD, es opcional (ver nota)
    private String descripcion;

    // Constructores
    public Permiso() {}

    public Permiso(String permiso) {
        this.permiso = permiso;
    }

    public Permiso(String permiso, String descripcion) {
        this.permiso = permiso;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Long getIdPermiso() { return idPermiso; }
    public void setIdPermiso(Long idPermiso) { this.idPermiso = idPermiso; }

    public String getPermiso() { return permiso; }
    public void setPermiso(String permiso) { this.permiso = permiso; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // equals y hashCode (recomendado para entidades JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permiso)) return false;
        Permiso permiso1 = (Permiso) o;
        return Objects.equals(getIdPermiso(), permiso1.getIdPermiso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPermiso());
    }

    @Override
    public String toString() {
        return "Permiso{" +
                "idPermiso=" + idPermiso +
                ", permiso='" + permiso + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
