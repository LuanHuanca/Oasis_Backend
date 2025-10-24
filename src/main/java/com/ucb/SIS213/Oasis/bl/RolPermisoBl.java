package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.RolPermisoDAO;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolPermisoBl {
    private final RolPermisoDAO rolPermisoDAO;

    @Autowired
    public RolPermisoBl(RolPermisoDAO rolPermisoDAO) {
        this.rolPermisoDAO = rolPermisoDAO;
    }

    public List<RolPermiso> getAllRolPermisos() {
        return rolPermisoDAO.findAll();
    }

    public RolPermiso getRolPermisoById(Long id) {
        return rolPermisoDAO.findById(id).orElse(null);
    }

    public RolPermiso createRolPermiso(RolPermiso rolPermiso) {
        return rolPermisoDAO.save(rolPermiso);
    }

    public List<RolPermiso> getPermisosByRolId(Long rolId) {
        return rolPermisoDAO.findPermisosByRolId(rolId);
    }

    public List<Permiso> findOnlyPermisosByRolId(Long rolId) {
        return rolPermisoDAO.findOnlyPermisosByRolId(rolId);
    }

    public void deleteRolPermiso(Long id) {
        rolPermisoDAO.deleteById(id);
    }

    public void deleteRolPermisoByRolIdAndPermisoId(Long rolId, Long permisoId) {
        rolPermisoDAO.deleteByRolIdAndPermisoId(rolId, permisoId);
    }
}