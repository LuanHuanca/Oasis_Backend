package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolPermisoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/rolpermiso")
public class RolPermisoAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolPermisoAPI.class);

    private RolPermisoBl rolPermisoBl;

    @Autowired
    public RolPermisoAPI(RolPermisoBl rolPermisoBl) {
        this.rolPermisoBl = rolPermisoBl;
    }

    // Endpoint para obtener todas las relaciones rol-permiso
    @GetMapping
    public ResponseDTO getAllRolPermisos() {
        List<RolPermiso> rolPermisos;
        try {
            rolPermisos = rolPermisoBl.getAllRolPermisos();
            LOGGER.info("Relaciones rol-permiso encontradas");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener las relaciones rol-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rolPermisos);
    }

    // Endpoint para obtener una relación rol-permiso por su id
    @GetMapping("/{id}")
    public ResponseDTO getRolPermisoById(@PathVariable Long id) {
        RolPermiso rolPermiso;
        try {
            rolPermiso = rolPermisoBl.getRolPermisoById(id);
            LOGGER.info("Relación rol-permiso encontrada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener la relación rol-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rolPermiso);
    }

    // Endpoint para crear una nueva relación rol-permiso
    @PostMapping("/create")
    public ResponseDTO createRolPermiso(@RequestBody RolPermiso rolPermiso) {
        RolPermiso rolPermisoCreado;
        try {
            rolPermisoCreado = rolPermisoBl.createRolPermiso(rolPermiso);
            LOGGER.info("Relación rol-permiso creada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear la relación rol-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rolPermisoCreado);
    }

    // Endpoint para obtener los permisos según el ID del administrador
    @GetMapping("/admin/{adminId}")
    public ResponseDTO getPermisosByAdminId(@PathVariable Long adminId) {
        List<RolPermiso> permisos;
        try {
            permisos = rolPermisoBl.getPermisosByAdminId(adminId);
            LOGGER.info("Permisos encontrados para el administrador con ID: " + adminId);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener los permisos para el administrador con ID: " + adminId, e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(permisos);
    }
}
