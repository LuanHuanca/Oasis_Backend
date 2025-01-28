package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.RolBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/rol")
public class RolAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolAPI.class);

    private RolBl rolBl;

    @Autowired
    public RolAPI(RolBl rolBl) {
        this.rolBl = rolBl;
    }

    // Endpoint para obtener todos los roles
    @GetMapping
    public ResponseDTO getAllRoles() {
        List<Rol> roles;
        try {
            roles = rolBl.getAllRoles();
            LOGGER.info("Roles encontrados");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener los roles", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(roles);
    }

    // Endpoint para obtener un rol por su id
    @GetMapping("/{id}")
    public ResponseDTO getRolById(@PathVariable Long id) {
        Rol rol;
        try {
            rol = rolBl.getRolById(id);
            LOGGER.info("Rol encontrado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener el rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rol);
    }

    // Endpoint para crear un nuevo rol
    @PostMapping("/create")
    public ResponseDTO createRol(@RequestBody Rol rol) {
        Rol rolCreado;
        try {
            rolCreado = rolBl.createRol(rol);
            LOGGER.info("Rol creado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear el rol", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rolCreado);
    }

    @PutMapping("/update")
    public ResponseDTO updateRol(@RequestBody Rol rol) {
        Rol rolActualizado;
        try {
            rolActualizado = rolBl.updateRol(rol);
        } catch (Exception e) {
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(rolActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteRol(@PathVariable Long id) {
        try {
            rolBl.deleteRol(id);
        } catch (Exception e) {
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Rol eliminado");
    }
}