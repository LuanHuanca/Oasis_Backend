package com.ucb.SIS213.Oasis.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Facturacion")
public class Facturacion {

    /*
    -- Table: Facturacion
    CREATE TABLE Facturacion (
         idFactura serial  NOT NULL,
         NIT varchar(45)  NOT NULL,
         fecha date  NOT NULL,
         Cliente_idCliente int  NOT NULL,
         ReservaViaje_idReservaViaja int  NOT NULL,
         FormaPago_idFormP int  NOT NULL,
         CONSTRAINT Facturacion_pk PRIMARY KEY (idFactura)
    );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private Long idFactura;

    @Column(name = "nit")
    private String NIT;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cliente_idcliente")
    private Long clienteidCliente;

    @Column(name = "reservaviaje_idreservaviaja")
    private Long reservaViajeidReservaViaja;

    @Column(name = "formapago_idformp")
    private Long formaPagoidFormP;

    // Getters y Setters


    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(Long clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }

    public Long getReservaViajeidReservaViaja() {
        return reservaViajeidReservaViaja;
    }

    public void setReservaViajeidReservaViaja(Long reservaViajeidReservaViaja) {
        this.reservaViajeidReservaViaja = reservaViajeidReservaViaja;
    }

    public Long getFormaPagoidFormP() {
        return formaPagoidFormP;
    }

    public void setFormaPagoidFormP(Long formaPagoidFormP) {
        this.formaPagoidFormP = formaPagoidFormP;
    }

    @Override
    public String toString() {
        return "Facturacion{" +
                "idFactura=" + idFactura +
                ", NIT='" + NIT + '\'' +
                ", fecha=" + fecha +
                ", Cliente_idCliente=" + clienteidCliente +
                ", ReservaViaje_idReservaViaja=" + reservaViajeidReservaViaja +
                ", FormaPago_idFormP=" + formaPagoidFormP +
                '}';
    }

}
