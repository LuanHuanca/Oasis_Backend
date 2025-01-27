package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AlquilerAutoDao;
import com.ucb.SIS213.Oasis.entity.AlquilerAuto;

import java.util.List;

@Service
public class AlquilerAutoBl {

    private AlquilerAutoDao alquilerAutoDao;

    @Autowired
    public AlquilerAutoBl(AlquilerAutoDao alquilerAutoDao) {
        this.alquilerAutoDao = alquilerAutoDao;
    }

    public List<AlquilerAuto> getAllAlquilerAuto() {
        return alquilerAutoDao.findAll();
    }

    public AlquilerAuto getAlquilerAutoById(Long id) {
        AlquilerAuto alquilerAuto = alquilerAutoDao.findById(id).orElse(null);
        if (alquilerAuto == null) {
            throw new RuntimeException("AlquilerAuto does not exist");
        }
        return alquilerAuto;
    }

    public AlquilerAuto createAlquilerAuto(AlquilerAuto alquilerAuto) {
        return alquilerAutoDao.save(alquilerAuto);
    }

    public AlquilerAuto updateAlquilerAutoById(Long id, AlquilerAuto alquilerAuto) {
        AlquilerAuto alquilerAutoActual = alquilerAutoDao.findById(id).orElse(null);
        if (alquilerAutoActual == null) {
            throw new RuntimeException("AlquilerAuto does not exist");
        }
        alquilerAutoActual.setPrecio(alquilerAuto.getPrecio());
        alquilerAutoActual.setDias(alquilerAuto.getDias());
        alquilerAutoActual.setEmpresa(alquilerAuto.getEmpresa());
        alquilerAutoActual.setIdAuto(alquilerAuto.getIdAuto());
        alquilerAutoActual.setIdCiudad(alquilerAuto.getIdCiudad());
        return alquilerAutoDao.save(alquilerAutoActual);
    }

    public void deleteAlquilerAutoById(Long id) {
        AlquilerAuto alquilerAuto = alquilerAutoDao.findById(id).orElse(null);
        if (alquilerAuto == null) {
            throw new RuntimeException("AlquilerAuto does not exist");
        }
        alquilerAutoDao.delete(alquilerAuto);
    }
}
