package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.RolPermisoDao;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolPermisoBl {
    private RolPermisoDao rolPermisoDao;

    @Autowired
    public RolPermisoBl(RolPermisoDao rolPermisoDao) {
        this.rolPermisoDao = rolPermisoDao;
    }

    public List<RolPermiso> getAllRolPermisos() {
        return rolPermisoDao.findAll();
    }

    public RolPermiso getRolPermisoById(Long id) {
        return rolPermisoDao.findById(id).orElse(null);
    }

    public RolPermiso createRolPermiso(RolPermiso rolPermiso) {
        return rolPermisoDao.save(rolPermiso);
    }

    public RolPermiso updateRolPermiso(RolPermiso rolPermiso) {
        return rolPermisoDao.save(rolPermiso);
    }

    public List<RolPermiso> getPermisosByAdminId(Long adminId) {
        return rolPermisoDao.findPermisosByAdminId(adminId);
    }

    public void deleteRolPermiso(Long id) {
        rolPermisoDao.deleteById(id);
    }
}
