package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.FacturacionDao;
import com.ucb.SIS213.Oasis.entity.Facturacion;

import java.util.List;

@Service
public class FacturacionBl {

    private FacturacionDao facturacionDao;

    @Autowired
    public FacturacionBl(FacturacionDao facturacionDao) {
        this.facturacionDao = facturacionDao;
    }

    public List<Facturacion> getAllFacturacion() {
        return facturacionDao.findAll();
    }

    public Facturacion getFacturacionById(Long id) {
        Facturacion facturacion = facturacionDao.findById(id).orElse(null);
        if (facturacion == null) {
            throw new RuntimeException("Facturacion does not exist");
        }
        return facturacion;
    }

    public Facturacion createFacturacion(Facturacion facturacion) {
        return facturacionDao.save(facturacion);
    }

    public Facturacion getFacturacionByNIT(String nit) {
        Facturacion facturacion = facturacionDao.findByNIT(nit);
        if (facturacion == null) {
            throw new RuntimeException("Facturacion does not exist");
        }
        return facturacion;
    }

    public Facturacion updateFacturacionById(Long id, Facturacion facturacion) {
        Facturacion facturacionActual = facturacionDao.findById(id).orElse(null);
        if (facturacionActual == null) {
            throw new RuntimeException("Facturacion does not exist");
        }
        facturacionActual.setNIT(facturacion.getNIT());
        return facturacionDao.save(facturacionActual);
    }

    public void deleteFacturacionById(Long id) {
        Facturacion facturacion = facturacionDao.findById(id).orElse(null);
        if (facturacion == null) {
            throw new RuntimeException("Facturacion does not exist");
        }
        facturacionDao.delete(facturacion);
    }

    public Long getLastIdFacturacion () {
        return facturacionDao.getLastInsertedPersonaId();
    }

}
