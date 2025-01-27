package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AerolineaDao;
import com.ucb.SIS213.Oasis.entity.Aerolinea;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.List;

@Service
public class AerolineaBl {

    private AerolineaDao aerolineaDao;

    @Autowired
    public AerolineaBl(AerolineaDao aerolineaDao) {
        this.aerolineaDao = aerolineaDao;
    }

    public List<Aerolinea> getAllAerolinea() {
        return aerolineaDao.findAll();
    }

    public Aerolinea getAerolineaById(Long id) {
        Aerolinea aerolinea = aerolineaDao.findById(id).orElse(null);
        if (aerolinea == null) {
            throw new RuntimeException("Aerolinea does not exist");
        }
        return aerolinea;
    }

    public Aerolinea createAerolinea(Aerolinea aerolinea) {
        return aerolineaDao.save(aerolinea);
    }

    public Aerolinea getAerolineaByNombre(String nombre) {
        Aerolinea aerolinea = aerolineaDao.findByAerolinea(nombre);
        if (aerolinea == null) {
            throw new RuntimeException("Aerolinea does not exist");
        }
        return aerolinea;
    }

    public Aerolinea updateAerolineaById(Long id, Aerolinea aerolinea) {
        Aerolinea aerolineaActual = aerolineaDao.findById(id).orElse(null);
        if (aerolineaActual == null) {
            throw new RuntimeException("Aerolinea does not exist");
        }
        aerolineaActual.setAerolinea(aerolinea.getAerolinea());
        return aerolineaDao.save(aerolineaActual);
    }

    public void deleteAerolineaById(Long id) {
        Aerolinea aerolinea = aerolineaDao.findById(id).orElse(null);
        if (aerolinea == null) {
            throw new RuntimeException("Aerolinea does not exist");
        }
        aerolineaDao.delete(aerolinea);
    }
}
