package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.CiudadDao;
import com.ucb.SIS213.Oasis.entity.Ciudad;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.List;

@Service
public class CiudadBl {

    private CiudadDao ciudadDao;

    @Autowired
    public CiudadBl(CiudadDao ciudadDao) {
        this.ciudadDao = ciudadDao;
    }

    public List<Ciudad> getAllCiudad() {
        return ciudadDao.findAll();
    }

    public Ciudad getCiudadById(Long id) {
        Ciudad ciudad = ciudadDao.findById(id).orElse(null);
        if (ciudad == null) {
            throw new RuntimeException("Ciudad does not exist");
        }
        return ciudad;
    }

    public Ciudad createCiudad(Ciudad ciudad) {
        return ciudadDao.save(ciudad);
    }

    public Ciudad updateCiudadById (Long id, Ciudad ciudad) {
        Ciudad ciudadActual = ciudadDao.findById(id).orElse(null);
        if (ciudadActual == null) {
            throw new RuntimeException("Ciudad does not exist");
        }
        ciudadActual.setCiudad(ciudad.getCiudad());
        ciudadActual.setIdPais(ciudad.getIdPais());
        ciudadActual.setIdCiudad(ciudad.getIdCiudad());
        return ciudadDao.save(ciudadActual);
    }

    public Ciudad deleteCiudadById(Long id) {
        Ciudad ciudad = ciudadDao.findById(id).orElse(null);
        if (ciudad == null) {
            throw new RuntimeException("Ciudad does not exist");
        }
        ciudadDao.deleteById(id);
        return ciudad;
    }
}
