package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.FormaPagoDao;
import com.ucb.SIS213.Oasis.entity.FormaPago;

import java.util.List;

@Service
public class FormaPagoBl {

    private FormaPagoDao formaPagoDao;

    @Autowired
    public FormaPagoBl(FormaPagoDao formaPagoDao) {
        this.formaPagoDao = formaPagoDao;
    }

    public List<FormaPago> getAllFormaPago() {
        return formaPagoDao.findAll();
    }

    public FormaPago getFormaPagoById(Long id) {
        FormaPago formaPago = formaPagoDao.findById(id).orElse(null);
        if (formaPago == null) {
            throw new RuntimeException("Forma de pago does not exist");
        }
        return formaPago;
    }

    public FormaPago createFormaPago(FormaPago formaPago) {
        return formaPagoDao.save(formaPago);
    }

    public FormaPago getFormaPagoByNombre(String nombre) {
        FormaPago formaPago = formaPagoDao.findByFormapago(nombre);
        if (formaPago == null) {
            throw new RuntimeException("Forma de pago does not exist");
        }
        return formaPago;
    }

    public FormaPago updateFormaPagoById(Long id, FormaPago formaPago) {
        FormaPago formaPagoActual = formaPagoDao.findById(id).orElse(null);
        if (formaPagoActual == null) {
            throw new RuntimeException("Forma de pago does not exist");
        }
        formaPagoActual.setFormapago(formaPago.getFormapago());
        return formaPagoDao.save(formaPagoActual);
    }

    public void deleteFormaPagoById(Long id) {
        FormaPago formaPago = formaPagoDao.findById(id).orElse(null);
        if (formaPago == null) {
            throw new RuntimeException("Forma de pago does not exist");
        }
        formaPagoDao.delete(formaPago);
    }


}
