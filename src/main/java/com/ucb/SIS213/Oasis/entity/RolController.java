package com.ucb.SIS213.Oasis.controller;

import com.ucb.SIS213.Oasis.entity.Rol;
import com.ucb.SIS213.Oasis.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    // Obtener todos los roles
    @GetMapping
    public List<Rol> getAllRoles() {
        return rolService.getAllRoles();
    }

    // Obtener un rol por ID
    @GetMapping("/{id}")
    public Rol getRolById(@PathVariable Integer id) {
        return rolService.getRolById(id);
    }

    // Crear un nuevo rol
    @PostMapping
    public Rol createRol(@RequestBody Rol rol) {
        return rolService.createRol(rol);
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public void deleteRol(@PathVariable Integer id) {
        rolService.deleteRol(id);
    }
}
