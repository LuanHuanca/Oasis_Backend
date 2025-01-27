package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.PermisoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/permiso")
public class PermisoAPI {
    private PermisoBl permisoBl;

    @Autowired
    public PermisoAPI(PermisoBl permisoBl) {
        this.permisoBl = permisoBl;
    }

    @GetMapping
    public ResponseDTO getAllPermisos() {
        List<Permiso> permisoList = permisoBl.getAllPermisos();
        return new ResponseDTO(permisoList);
    }

    @GetMapping("/{id}")
    public ResponseDTO getPermisoById(@PathVariable Long id) {
        Permiso permiso = permisoBl.getPermisoById(id);
        return new ResponseDTO(permiso);
    }

    @PostMapping("/create")
    public ResponseDTO createPermiso(@RequestBody Permiso permiso) {
        Permiso permisoCreado = permisoBl.createPermiso(permiso);
        return new ResponseDTO(permisoCreado);
    }

    @PutMapping("/update")
    public ResponseDTO updatePermiso(@RequestBody Permiso permiso) {
        Permiso permisoActualizado = permisoBl.updatePermiso(permiso);
        return new ResponseDTO(permisoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deletePermiso(@PathVariable Long id) {
        permisoBl.deletePermiso(id);
        return new ResponseDTO("Permiso eliminado");
    }
}
