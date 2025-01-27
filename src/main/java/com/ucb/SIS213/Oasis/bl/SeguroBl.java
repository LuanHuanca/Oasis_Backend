package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.SeguroDao;
import com.ucb.SIS213.Oasis.entity.Seguro;

import java.util.List;

@Service
public class SeguroBl {

    private SeguroDao seguroDao;

    @Autowired
    public SeguroBl(SeguroDao seguroDao) {
        this.seguroDao = seguroDao;
    }

    public List<Seguro> getAllSeguro() {
        return seguroDao.findAll();
    }

    public Seguro getSeguroById(Long id) {
        Seguro seguro = seguroDao.findById(id).orElse(null);
        if (seguro == null) {
            throw new RuntimeException("Seguro does not exist");
        }
        return seguro;
    }

    public Seguro createSeguro(Seguro seguro) {
        return seguroDao.save(seguro);
    }

    public Seguro updateSeguroById(Long id, Seguro seguro) {
        Seguro seguroActual = seguroDao.findById(id).orElse(null);
        if (seguroActual == null) {
            throw new RuntimeException("Seguro does not exist");
        }
        seguroActual.setSeguro(seguro.getSeguro());
        seguroActual.setCobertura(seguro.getCobertura());
        seguroActual.setPrecio(seguro.getPrecio());
        return seguroDao.save(seguroActual);
    }

    public void deleteSeguroById(Long id) {
        Seguro seguro = seguroDao.findById(id).orElse(null);
        if (seguro == null) {
            throw new RuntimeException("Seguro does not exist");
        }
        seguroDao.delete(seguro);
    }
}
