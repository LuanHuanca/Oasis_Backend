package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AtraccionDao;
import com.ucb.SIS213.Oasis.entity.Atraccion;

import java.util.List;

@Service
public class AtraccionBl {

    private AtraccionDao atraccionDao;

    @Autowired
    public AtraccionBl(AtraccionDao atraccionDao) {
        this.atraccionDao = atraccionDao;
    }

    public List<Atraccion> getAllAtraccion() {
        return atraccionDao.findAll();
    }

    public Atraccion getAtraccionById(Long id) {
        Atraccion atraccion = atraccionDao.findById(id).orElse(null);
        if (atraccion == null) {
            throw new RuntimeException("Atraccion does not exist");
        }
        return atraccion;
    }

    public Atraccion createAtraccion(Atraccion atraccion) {
        return atraccionDao.save(atraccion);
    }

    public Atraccion updateAtraccionById(Long id, Atraccion atraccion) {
        Atraccion atraccionActual = atraccionDao.findById(id).orElse(null);
        if (atraccionActual == null) {
            throw new RuntimeException("Atraccion does not exist");
        }
        atraccionActual.setAtraccion(atraccion.getAtraccion());
        atraccionActual.setAtraccion(atraccion.getAtraccion());
        atraccionActual.setIdCiudad(atraccion.getIdCiudad());
        atraccionActual.setIdCatAtrac(atraccion.getIdCatAtrac());
        atraccionActual.setPrecio(atraccion.getPrecio());
        atraccionActual.setDetalle(atraccion.getDetalle());
        return atraccionDao.save(atraccionActual);
    }

    public void deleteAtraccionById(Long id) {
        Atraccion atraccion = atraccionDao.findById(id).orElse(null);
        if (atraccion == null) {
            throw new RuntimeException("Atraccion does not exist");
        }
        atraccionDao.delete(atraccion);
    }
}
