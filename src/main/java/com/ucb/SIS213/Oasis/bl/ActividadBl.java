package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.ActividadDao;
import com.ucb.SIS213.Oasis.entity.Actividad;

import java.util.List;

@Service
public class ActividadBl {

    private ActividadDao actividadDao;

    @Autowired
    public ActividadBl(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }

    public List<Actividad> getAllActividad() {
        return actividadDao.findAll();
    }

    public Actividad getActividadById(Long id) {
        Actividad actividad = actividadDao.findById(id).orElse(null);
        if (actividad == null) {
            throw new RuntimeException("Actividad does not exist");
        }
        return actividad;
    }

    public Actividad createActividad(Actividad actividad) {
        return actividadDao.save(actividad);
    }

    public Actividad updateActividadById(Long id, Actividad actividad) {
        Actividad actividadActual = actividadDao.findById(id).orElse(null);
        if (actividadActual == null) {
            throw new RuntimeException("Actividad does not exist");
        }
        actividadActual.setActividad(actividad.getActividad());
        actividadActual.setIdCiudad(actividad.getIdCiudad());
        actividadActual.setFecha(actividad.getFecha());
        actividadActual.setIdCatActi(actividad.getIdCatActi());
        actividadActual.setFecha(actividad.getFecha());
        actividadActual.setPrecio(actividad.getPrecio());
        actividadActual.setDetalle(actividad.getDetalle());
        return actividadDao.save(actividadActual);
    }

    public void deleteActividadById(Long id) {
        Actividad actividad = actividadDao.findById(id).orElse(null);
        if (actividad == null) {
            throw new RuntimeException("Actividad does not exist");
        }
        actividadDao.delete(actividad);
    }
}
