package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.AdminPermisoDAO;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPermisoBl {
    private AdminPermisoDAO adminPermisoDAO;

    @Autowired
    public AdminPermisoBl(AdminPermisoDAO adminPermisoDAO) {
        this.adminPermisoDAO = adminPermisoDAO;
    }

    public List<AdminPermiso> getAllAdminPermisos() {
        return adminPermisoDAO.findAll();
    }

    public AdminPermiso getAdminPermisoById(Long id) {
        return adminPermisoDAO.findById(id).orElse(null);
    }

    public AdminPermiso createAdminPermiso(AdminPermiso adminPermiso) {
        return adminPermisoDAO.save(adminPermiso);
    }

    public List<AdminPermiso> getPermisosByAdminId(Long adminId) {
        return adminPermisoDAO.findPermisosByAdminId(adminId);
    }
}