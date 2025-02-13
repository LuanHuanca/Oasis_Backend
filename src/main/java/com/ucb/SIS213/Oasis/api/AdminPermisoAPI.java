package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.AdminPermisoBl;
import com.ucb.SIS213.Oasis.dto.PermisoDTO;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

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
    // Nuevo endpoint para obtener permisos por ID de administrador
    @GetMapping("/admin/{adminId}/permisos")
    public ResponseDTO getOnlyPermisosbyId(@PathVariable Long adminId) {
        List<Permiso> permisos = adminPermisoBl.findOnlyPermisosByAdminId(adminId);
        List<PermisoDTO> permisoDTOs = permisos.stream()
                .map(permiso -> new PermisoDTO(permiso.getIdPermiso(), permiso.getPermiso()))
                .collect(Collectors.toList());
        return new ResponseDTO(permisoDTOs);
    }
    
    // Endpoint para actualizar una relación admin-permiso
    @PutMapping("/update")
    public ResponseDTO updateAdminPermiso(@RequestBody AdminPermiso adminPermiso) {
        AdminPermiso adminPermisoActualizado;
        try {
            adminPermisoActualizado = adminPermisoBl.updateAdminPermiso(adminPermiso);
            LOGGER.info("Relación admin-permiso actualizada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar la relación admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminPermisoActualizado);
    }

    // Endpoint para eliminar una relación admin-permiso
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAdminPermiso(@PathVariable Long id) {
        try {
            adminPermisoBl.deleteAdminPermiso(id);
            LOGGER.info("Relación admin-permiso eliminada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar la relación admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Relación admin-permiso eliminada");
    }

    // Endpoint para eliminar una relación admin-permiso específica
    @DeleteMapping("/delete/{adminId}/{permisoId}")
    public ResponseDTO deleteAdminPermiso(@PathVariable Long adminId, @PathVariable Long permisoId) {
        try {
            adminPermisoBl.deleteAdminPermisoByAdminIdAndPermisoId(adminId, permisoId);
            LOGGER.info("Relación admin-permiso eliminada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar la relación admin-permiso", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Relación admin-permiso eliminada");
    }
}