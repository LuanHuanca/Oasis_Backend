package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Formapago")
public class FormaPago {

    /*
    -- Table: FormaPago
      CREATE TABLE FormaPago (
       idFormP serial  NOT NULL,
       formapago varchar(45)  NOT NULL,
       CONSTRAINT FormaPago_pk PRIMARY KEY (idFormP)
        );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idformp")
    private Long idFormP;

    @Column(name = "formapago")
    private String formapago;

    // Getters y Setters

    public Long getIdFormP() {
        return idFormP;
    }

    public void setIdFormP(Long idFormP) {
        this.idFormP = idFormP;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    @Override
    public String toString() {
        return "FormaPago{" +
                "idFormP=" + idFormP +
                ", formapago='" + formapago + '\'' +
                '}';
    }


}
