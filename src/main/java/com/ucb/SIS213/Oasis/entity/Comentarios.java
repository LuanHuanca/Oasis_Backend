package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Comentarios")
public class Comentarios {

    /*
    -- Table: Comentarios
    CREATE TABLE Comentarios (
     idComentario serial  NOT NULL,
     Comentario text  NOT NULL,
     Cliente_idCliente int  NOT NULL,
     CONSTRAINT Comentarios_pk PRIMARY KEY (idComentario)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomentario")
    private Long idComentario;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "cliente_idcliente")
    private Long clienteidCliente;

    // Getters y Setters


    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(Long clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }
}
