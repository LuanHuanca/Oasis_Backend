package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolPermisoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/rolpermiso")
public class RolPermisoAPI {
    private RolPermisoBl rolPermisoBl;

    @Autowired
    public RolPermisoAPI(RolPermisoBl rolPermisoBl) {
        this.rolPermisoBl = rolPermisoBl;
    }

    @GetMapping
    public ResponseDTO getAllRolPermisos() {
        List<RolPermiso> rolPermisoList = rolPermisoBl.getAllRolPermisos();
        return new ResponseDTO(rolPermisoList);
    }

    @GetMapping("/{id}")
    public ResponseDTO getRolPermisoById(@PathVariable Long id) {
        RolPermiso rolPermiso = rolPermisoBl.getRolPermisoById(id);
        return new ResponseDTO(rolPermiso);
    }

    @PostMapping("/create")
    public ResponseDTO createRolPermiso(@RequestBody RolPermiso rolPermiso) {
        RolPermiso rolPermisoCreado = rolPermisoBl.createRolPermiso(rolPermiso);
        return new ResponseDTO(rolPermisoCreado);
    }

    @PutMapping("/update")
    public ResponseDTO updateRolPermiso(@RequestBody RolPermiso rolPermiso) {
        RolPermiso rolPermisoActualizado = rolPermisoBl.updateRolPermiso(rolPermiso);
        return new ResponseDTO(rolPermisoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteRolPermiso(@PathVariable Long id) {
        rolPermisoBl.deleteRolPermiso(id);
        return new ResponseDTO("RolPermiso eliminado");
    }
}
