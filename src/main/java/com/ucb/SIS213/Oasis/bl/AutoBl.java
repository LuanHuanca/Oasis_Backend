package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AutoDao;
import com.ucb.SIS213.Oasis.entity.Auto;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.List;

@Service
public class AutoBl {

    private AutoDao autoDao;

    @Autowired
    public AutoBl(AutoDao autoDao) {
        this.autoDao = autoDao;
    }

    public List<Auto> getAllAuto() {
        return autoDao.findAll();
    }

    public Auto getAutoById(Long id) {
        Auto auto = autoDao.findById(id).orElse(null);
        if (auto == null) {
            throw new RuntimeException("Auto does not exist");
        }
        return auto;
    }

    public Auto createAuto(Auto auto) {
        return autoDao.save(auto);
    }

    public Auto updateAutoById (Long id, Auto auto) {
        Auto autoActual = autoDao.findById(id).orElse(null);
        if (autoActual == null) {
            throw new RuntimeException("Auto does not exist");
        }
        autoActual.setModelo(auto.getModelo());
        autoActual.setMarca(auto.getMarca());
        autoActual.setTipo(auto.getTipo());
        autoActual.setIdAuto(auto.getIdAuto());
        return autoDao.save(autoActual);
    }

    public Auto deleteAutoById(Long id) {
        Auto auto = autoDao.findById(id).orElse(null);
        if (auto == null) {
            throw new RuntimeException("Auto does not exist");
        }
        autoDao.deleteById(id);
        return auto;
    }
}
