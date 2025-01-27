package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.RolDao;
import com.ucb.SIS213.Oasis.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolBl {
    private RolDao rolDao;

    @Autowired
    public RolBl(RolDao rolDao) {
        this.rolDao = rolDao;
    }

    public List<Rol> getAllRoles() {
        return rolDao.findAll();
    }

    public Rol getRolById(Long id) {
        return rolDao.findById(id).orElse(null);
    }

    public Rol createRol(Rol rol) {
        return rolDao.save(rol);
    }

    public Rol updateRol(Rol rol) {
        return rolDao.save(rol);
    }

    public void deleteRol(Long id) {
        rolDao.deleteById(id);
    }
}
