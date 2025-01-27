package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.PermisoDao;
import com.ucb.SIS213.Oasis.entity.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoBl {
    private PermisoDao permisoDao;

    @Autowired
    public PermisoBl(PermisoDao permisoDao) {
        this.permisoDao = permisoDao;
    }

    public List<Permiso> getAllPermisos() {
        return permisoDao.findAll();
    }

    public Permiso getPermisoById(Long id) {
        return permisoDao.findById(id).orElse(null);
    }

    public Permiso createPermiso(Permiso permiso) {
        return permisoDao.save(permiso);
    }

    public Permiso updatePermiso(Permiso permiso) {
        return permisoDao.save(permiso);
    }

    public void deletePermiso(Long id) {
        permisoDao.deleteById(id);
    }
}
