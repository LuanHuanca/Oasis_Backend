package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.ViajeDao;
import com.ucb.SIS213.Oasis.entity.Viaje;

import java.util.List;

@Service
public class ViajeBl {

    private ViajeDao viajeDao;

    @Autowired
    public ViajeBl(ViajeDao viajeDao) {
        this.viajeDao = viajeDao;
    }

    public List<Viaje> getAllViaje() {
        return viajeDao.findAll();
    }

    public Viaje getViajeById(Long id) {
        Viaje viaje = viajeDao.findById(id).orElse(null);
        if (viaje == null) {
            throw new RuntimeException("Viaje does not exist");
        }
        return viaje;
    }

    public Viaje createViaje(Viaje viaje) {
        return viajeDao.save(viaje);
    }

    public Viaje updateViajeById(Long id, Viaje viaje) {
        Viaje viajeActual = viajeDao.findById(id).orElse(null);
        if (viajeActual == null) {
            throw new RuntimeException("Viaje does not exist");
        }
        viajeActual.setFecha(viaje.getFecha());
        viajeActual.setidCliente(viaje.getidCliente());
        viajeActual.setidViaje(viaje.getidViaje());
        viajeActual.setidSeguro(viaje.getidSeguro());
        viajeActual.setidAlquiler(viaje.getidAlquiler());
        viajeActual.setidAtraccion(viaje.getidAtraccion());
        viajeActual.setidActividad(viaje.getidActividad());
        viajeActual.setidReservaHotel(viaje.getidReservaHotel());
        return viajeDao.save(viajeActual);
    }

    public void deleteViajeById(Long id) {
        Viaje viaje = viajeDao.findById(id).orElse(null);
        if (viaje == null) {
            throw new RuntimeException("Viaje does not exist");
        }
        viajeDao.delete(viaje);
    }

    public Long getLastIdViaje () {
        return viajeDao.getLastIdViaje();
    }
}
