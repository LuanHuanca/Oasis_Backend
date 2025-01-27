package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/rol")
public class RolAPI {
    private RolBl rolBl;

    @Autowired
    public RolAPI(RolBl rolBl) {
        this.rolBl = rolBl;
    }

    @GetMapping
    public ResponseDTO getAllRoles() {
        List<Rol> rolList = rolBl.getAllRoles();
        return new ResponseDTO(rolList);
    }

    @GetMapping("/{id}")
    public ResponseDTO getRolById(@PathVariable Long id) {
        Rol rol = rolBl.getRolById(id);
        return new ResponseDTO(rol);
    }

    @PostMapping("/create")
    public ResponseDTO createRol(@RequestBody Rol rol) {
        Rol rolCreado = rolBl.createRol(rol);
        return new ResponseDTO(rolCreado);
    }

    @PutMapping("/update")
    public ResponseDTO updateRol(@RequestBody Rol rol) {
        Rol rolActualizado = rolBl.updateRol(rol);
        return new ResponseDTO(rolActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteRol(@PathVariable Long id) {
        rolBl.deleteRol(id);
        return new ResponseDTO("Rol eliminado");
    }
}