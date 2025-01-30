package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.AdminBl;
import com.ucb.SIS213.Oasis.dto.AdminDTO;
import com.ucb.SIS213.Oasis.dto.LoginRequestDTO;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.Persona;
import com.ucb.SIS213.Oasis.entity.Rol;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.exep.UserException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/admin")
public class AdminAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAPI.class);

    private AdminBl adminBl;

    @Autowired
    public AdminAPI(AdminBl adminBl) {
        this.adminBl = adminBl;
    }

    // Endpoint para obtener todos los admins
    @GetMapping
    public ResponseDTO getAllAdmins() {
        List<Admin> adminList;
        try {
            adminList = adminBl.getAllAdmin();
            LOGGER.info("Admins encontrados");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener los admins");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminList);
    }

    // Endpoint para obtener un admin por su id
    @GetMapping("/{id}")
    public ResponseDTO getAdminById(@PathVariable Long id) {
        Admin admin;
        try {
            admin = adminBl.getAdminById(id);
            if (admin == null) {
                return new ResponseDTO("TASK-1001", "Admin no encontrado");
            }
            LOGGER.info("Admin encontrado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener el admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        // return new ResponseDTO(new AdminDTO(admin, permisos));
        return new ResponseDTO(admin);
    }

    @PostMapping("/create")
    public ResponseDTO createAdmin(@RequestBody Map<String, Object> requestBody) {
        // Obtener el objeto Persona de la solicitud
        Map<String, Object> personaMap = (Map<String, Object>) requestBody.get("persona");
        Persona persona = convertMapToPersona(personaMap);

        // Obtener id de la persona
        Long idPersona = persona.getIdPersona();

        // Crear correo de admin
        String correoAdmin = persona.getNombre() + "." + persona.getApellidoP() + "@oasis.bo";

        // Crear contraseña de admin
        String contrasenaAdmin = persona.getNombre() + persona.getApellidoP().substring(0, 1) + persona.getTelefono();

        // Recuperar Rol
        Integer rolId = (Integer) requestBody.get("rolId");

        LOGGER.info("PersonaID: " + idPersona);
        LOGGER.info("Correo: " + correoAdmin);
        LOGGER.info("Contraseña: " + contrasenaAdmin);
        LOGGER.info("Rol ID: " + rolId);

        Admin adminCreado = new Admin();
        adminCreado.setCorreo(correoAdmin);
        adminCreado.setPassword(contrasenaAdmin);
        adminCreado.setRol(new Rol(rolId)); // Usar el constructor que acepta un Integer
        adminCreado.setIdPersona(idPersona);

        try {
            adminCreado = adminBl.createAdmin(adminCreado);
            LOGGER.info("Admin creado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear el admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("");
    }

    private Persona convertMapToPersona(Map<String, Object> personaMap) {
        // Implementa la lógica para convertir el mapa en un objeto Persona
        Persona persona = new Persona();
        Integer idPersonaInteger = (Integer) personaMap.get("idPersona");
        Long idPersonaLong = Long.valueOf(idPersonaInteger.longValue());
        persona.setIdPersona(idPersonaLong);
        persona.setNombre((String) personaMap.get("nombre"));
        persona.setApellidoP((String) personaMap.get("apellidoP"));
        persona.setApellidoM((String) personaMap.get("apellidoM"));
        persona.setTelefono((String) personaMap.get("telefono"));

        return persona;
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Admin admin;
        try {
            admin = adminBl.login(loginRequestDTO.getCorreo(), loginRequestDTO.getPassword());
            LOGGER.info("Se realizo la autencitacion");
        } catch (UserException e) {
            LOGGER.error("NO Se realizo la autencitacion", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(admin);
    }

    // Endpoint para actualizar un admin
    @PutMapping("/update")
    public ResponseDTO updateAdmin(@RequestBody Admin admin) {
        Admin adminActualizado;
        try {
            adminActualizado = adminBl.updateAdmin(admin);
            LOGGER.info("Admin actualizado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar el admin");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminActualizado);
    }

    // Endpoint para actualizar el rol de un admin
    @PutMapping("/updateRole/{id}")
    public ResponseDTO updateAdminRole(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        Admin admin;
        try {
            admin = adminBl.getAdminById(id);
            if (admin == null) {
                return new ResponseDTO("TASK-1001", "Admin no encontrado");
            }
            Integer rolId = (Integer) requestBody.get("rolId");
            admin.setRol(new Rol(rolId));
            adminBl.updateAdmin(admin);
            LOGGER.info("Rol del admin actualizado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar el rol del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(admin);
    }

    // Endpoint para eliminar un admin
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAdmin(@PathVariable Long id) {
        try {
            adminBl.deleteAdmin(id);
            LOGGER.info("Admin eliminado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar el admin");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Admin eliminado");
    }
}
