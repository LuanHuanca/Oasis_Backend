package com.ucb.SIS213.Oasis.controller;

import com.ucb.SIS213.Oasis.entity.Rol;
import com.ucb.SIS213.Oasis.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        Rol rol = rolService.getRolById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody @Valid Rol rol) {
        if (rol == null || rol.getNombre() == null || rol.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Rol createdRol = rolService.createRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRol);
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        boolean deleted = rolService.deleteRol(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
