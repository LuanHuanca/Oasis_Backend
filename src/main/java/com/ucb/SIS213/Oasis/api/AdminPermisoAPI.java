package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.AdminPermisoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/adminpermiso")
public class AdminPermisoAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPermisoAPI.class);

    private AdminPermisoBl adminPermisoBl;

    @Autowired
    public AdminPermisoAPI(AdminPermisoBl adminPermisoBl) {
        this.adminPermisoBl = adminPermisoBl;
    }

    // Endpoint para obtener todas las relaciones admin-permiso
    @GetMapping
    public ResponseDTO getAllAdminPermisos() {
        List<AdminPermiso> adminPermisos;
        try {
            adminPermisos = adminPermisoBl.getAllAdminPermisos();
            LOGGER.info("Relaciones admin-permiso encontradas");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener las relaciones admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminPermisos);
    }

    // Endpoint para obtener una relación admin-permiso por su id
    @GetMapping("/{id}")
    public ResponseDTO getAdminPermisoById(@PathVariable Long id) {
        AdminPermiso adminPermiso;
        try {
            adminPermiso = adminPermisoBl.getAdminPermisoById(id);
            LOGGER.info("Relación admin-permiso encontrada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener la relación admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminPermiso);
    }

    // Endpoint para crear una nueva relación admin-permiso
    @PostMapping("/create")
    public ResponseDTO createAdminPermiso(@RequestBody AdminPermiso adminPermiso) {
        AdminPermiso adminPermisoCreado;
        try {
            adminPermisoCreado = adminPermisoBl.createAdminPermiso(adminPermiso);
            LOGGER.info("Relación admin-permiso creada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear la relación admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminPermisoCreado);
    }

    // Endpoint para obtener los permisos según el ID del administrador
    @GetMapping("/admin/{adminId}")
    public ResponseDTO getPermisosByAdminId(@PathVariable Long adminId) {
        List<AdminPermiso> permisos;
        try {
            permisos = adminPermisoBl.getPermisosByAdminId(adminId);
            LOGGER.info("Permisos encontrados para el administrador con ID: " + adminId);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener los permisos para el administrador con ID: " + adminId, e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(permisos);
    }
}