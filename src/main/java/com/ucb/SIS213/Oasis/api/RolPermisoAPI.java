package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolPermisoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;
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
    private final RolPermisoBl rolPermisoBl;

    @Autowired
    public RolPermisoAPI(RolPermisoBl rolPermisoBl) {
        this.rolPermisoBl = rolPermisoBl;
    }

    @GetMapping("")
    public ResponseDTO getAllRolPermisos() {
        try {
            List<RolPermiso> rolPermisos = rolPermisoBl.getAllRolPermisos();
            return new ResponseDTO(rolPermisos);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseDTO getRolPermisoById(@PathVariable Long id) {
        try {
            RolPermiso rolPermiso = rolPermisoBl.getRolPermisoById(id);
            return new ResponseDTO(rolPermiso);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseDTO createRolPermiso(@RequestBody RolPermiso rolPermiso) {
        try {
            RolPermiso newRolPermiso = rolPermisoBl.createRolPermiso(rolPermiso);
            return new ResponseDTO(newRolPermiso);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/rol/{rolId}")
    public ResponseDTO getPermisosByRolId(@PathVariable Long rolId) {
        try {
            List<Permiso> permisos = rolPermisoBl.findOnlyPermisosByRolId(rolId);
            return new ResponseDTO(permisos);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{rolId}/{permisoId}")
    public ResponseDTO deleteRolPermisoByIds(@PathVariable Long rolId, @PathVariable Long permisoId) {
        try {
            rolPermisoBl.deleteRolPermisoByRolIdAndPermisoId(rolId, permisoId);
            return new ResponseDTO("RolPermiso eliminado exitosamente");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}