package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.VueloDao;
import com.ucb.SIS213.Oasis.entity.Vuelo;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.Date;
import java.util.List;

@Service
public class VueloBl {

    private VueloDao vueloDao;

    @Autowired
    public VueloBl(VueloDao vueloDao) {
        this.vueloDao = vueloDao;
    }

    public List<Vuelo> getAllVuelo() {
        return vueloDao.findAll();
    }

    public Vuelo getVueloById(Long id) {
        Vuelo vuelo = vueloDao.findById(id).orElse(null);
        if (vuelo == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        return vuelo;
    }

    public Vuelo createVuelo(Vuelo vuelo) {
        return vueloDao.save(vuelo);
    }

    public Vuelo getVueloByOrigen(Long origen) {
        Vuelo vuelo = vueloDao.findByOrigen(origen);
        if (vuelo == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        return vuelo;
    }

    public Vuelo getVueloByDestino(Long destino) {
        Vuelo vuelo = vueloDao.findByDestino(destino);
        if (vuelo == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        return vuelo;
    }

    public Vuelo getVueloByFechaInicio(Date fechaInicio) {
        Vuelo vuelo = vueloDao.findByFechaInicio(fechaInicio);
        if (vuelo == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        return vuelo;
    }

    public Vuelo getVueloByFechaFin(Date fechaFin) {
        Vuelo vuelo = vueloDao.findByFechaFin(fechaFin);
        if (vuelo == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        return vuelo;
    }

    public Vuelo updateVueloById (Long id, Vuelo vuelo) {
        Vuelo vueloActualizado = vueloDao.findById(id).orElse(null);
        if (vueloActualizado == null) {
            throw new RuntimeException("Vuelo does not exist");
        }
        vueloActualizado.setOrigen(vuelo.getOrigen());
        vueloActualizado.setDestino(vuelo.getDestino());
        vueloActualizado.setIdAerolinea(vuelo.getIdAerolinea());
        vueloActualizado.setFechaInicio(vuelo.getFechaInicio());
        vueloActualizado.setFechaFin(vuelo.getFechaFin());
        vueloActualizado.setPrecio(vuelo.getPrecio());
        vueloActualizado.setPersonas(vuelo.getPersonas());
        vueloActualizado.setNroReserva(vuelo.getNroReserva());
        return vueloDao.save(vueloActualizado);
    }

    public void deleteVuelo(Long id) {
        vueloDao.deleteById(id);
    }

}
