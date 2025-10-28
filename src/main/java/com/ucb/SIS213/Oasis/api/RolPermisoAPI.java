package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolPermisoService;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Permiso;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rol-permiso")
@CrossOrigin(origins = "*")
public class RolPermisoAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RolPermisoAPI.class);

    @Autowired
    private RolPermisoService rolPermisoService;

    /**
     * Asigna un permiso a un rol
     */
    @PostMapping("/asignar")
    public ResponseDTO asignarPermisoARol(@RequestBody Map<String, Object> requestBody) {
        try {
            Long rolId = Long.valueOf(requestBody.get("rolId").toString());
            Long permisoId = Long.valueOf(requestBody.get("permisoId").toString());
            
            RolPermiso rolPermiso = rolPermisoService.asignarPermisoARol(rolId, permisoId);
            LOGGER.info("Permiso asignado al rol exitosamente");
            return new ResponseDTO(rolPermiso);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permiso al rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Asigna múltiples permisos a un rol
     */
    @PostMapping("/asignar-multiples")
    public ResponseDTO asignarPermisosARol(@RequestBody Map<String, Object> requestBody) {
        try {
            Long rolId = Long.valueOf(requestBody.get("rolId").toString());
            @SuppressWarnings("unchecked")
            List<Long> permisoIds = (List<Long>) requestBody.get("permisoIds");
            
            List<RolPermiso> rolPermisos = rolPermisoService.asignarPermisosARol(rolId, permisoIds);
            LOGGER.info("Permisos múltiples asignados al rol exitosamente");
            return new ResponseDTO(rolPermisos);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permisos múltiples al rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene todos los permisos de un rol
     */
    @GetMapping("/rol/{rolId}")
    public ResponseDTO getPermisosByRol(@PathVariable Long rolId) {
        try {
            List<RolPermiso> rolPermisos = rolPermisoService.getPermisosByRol(rolId);
            LOGGER.info("Permisos del rol obtenidos exitosamente");
            return new ResponseDTO(rolPermisos);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos del rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene solo los permisos (sin relación) de un rol
     */
    @GetMapping("/rol/{rolId}/permisos")
    public ResponseDTO getPermisosSoloByRol(@PathVariable Long rolId) {
        try {
            List<Permiso> permisos = rolPermisoService.getPermisosSoloByRol(rolId);
            LOGGER.info("Permisos del rol obtenidos exitosamente");
            return new ResponseDTO(permisos);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos del rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Elimina un permiso específico de un rol
     */
    @DeleteMapping("/rol/{rolId}/permiso/{permisoId}")
    public ResponseDTO removerPermisoDeRol(@PathVariable Long rolId, @PathVariable Long permisoId) {
        try {
            rolPermisoService.removerPermisoDeRol(rolId, permisoId);
            LOGGER.info("Permiso removido del rol exitosamente");
            return new ResponseDTO("Permiso removido exitosamente");
        } catch (RuntimeException e) {
            LOGGER.error("Error al remover permiso del rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Reemplaza todos los permisos de un rol
     */
    @PutMapping("/rol/{rolId}/reemplazar")
    public ResponseDTO reemplazarPermisosDeRol(@PathVariable Long rolId, @RequestBody Map<String, Object> requestBody) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> permisoIds = (List<Long>) requestBody.get("permisoIds");
            
            List<RolPermiso> rolPermisos = rolPermisoService.reemplazarPermisosDeRol(rolId, permisoIds);
            LOGGER.info("Permisos del rol reemplazados exitosamente");
            return new ResponseDTO(rolPermisos);
        } catch (RuntimeException e) {
            LOGGER.error("Error al reemplazar permisos del rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Verifica si un rol tiene un permiso específico
     */
    @GetMapping("/rol/{rolId}/tiene-permiso/{permisoId}")
    public ResponseDTO rolTienePermiso(@PathVariable Long rolId, @PathVariable Long permisoId) {
        try {
            boolean tienePermiso = rolPermisoService.rolTienePermiso(rolId, permisoId);
            LOGGER.info("Verificación de permiso del rol realizada");
            return new ResponseDTO(tienePermiso);
        } catch (RuntimeException e) {
            LOGGER.error("Error al verificar permiso del rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }
}
