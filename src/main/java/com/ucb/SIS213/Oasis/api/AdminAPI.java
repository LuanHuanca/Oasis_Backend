package com.ucb.SIS213.Oasis.api;

import java.util.List;
import java.util.Map;

import com.ucb.SIS213.Oasis.dto.LoginRequestDTO;
import com.ucb.SIS213.Oasis.exep.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AdminBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.Persona;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/admin")
public class AdminAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdminAPI.class);

    AdminBl adminBl;

    @Autowired
    public AdminAPI(AdminBl adminBl) {
        this.adminBl = adminBl;
    }

    // Endpoint para obtener todos los admins
    @GetMapping
    public ResponseDTO getAllAdmins() {
        List<Admin> adminList;
        try{
            adminList = adminBl.getAllAdmin();
            LOGGER.info("Admins encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los admins");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminList);
    }

    // Endpoint para obtener un admin por su id
    @GetMapping("/{id}")
    public ResponseDTO getAdminById(@PathVariable Long id) {
        Admin admin;
        try{
            admin = adminBl.getAdminById(id);
            LOGGER.info("Admin encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el admin");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(admin);
    }

    @PostMapping("/create")
    public ResponseDTO createAdmin(@RequestBody Map<String, Object> requestBody) {
        // Obtener el objeto Admin de la solicitud
        Map<String, Object> adminMap = (Map<String, Object>) requestBody.get("persona");
        Persona admin = convertMapToAdmin(adminMap);

        //Obtener id de la persona
        Long idPersona = admin.getIdPersona();
 
        // Crear correo de admin
        String correoAdmin = admin.getNombre().toString() + "." + admin.getApellidoP().toString()+ "@oasis.bo";

        // Crear contraseña de admin
        String contrasenaAdmin = "OASIS1590";

        // Recuperar Rol
        String rol = (String) requestBody.get("rol");

        LOGGER.info("PersonaID: " + idPersona);
        LOGGER.info("Correo: " + correoAdmin);
        LOGGER.info("Contraseña: " + contrasenaAdmin);
        LOGGER.info("Rol: " + rol);

        Admin adminCreado = new Admin();
        adminCreado.setCorreo(correoAdmin);
        adminCreado.setPassword(contrasenaAdmin);
        adminCreado.setRol(rol);
        adminCreado.setIdPersona(idPersona);

        try {
            adminCreado = adminBl.createAdmin(adminCreado);
            LOGGER.info("Admin creado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear el admin");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("");
    }

    private Persona convertMapToAdmin(Map<String, Object> adminMap) {
        // Implementa la lógica para convertir el mapa en un objeto Admin
        // Esto puede implicar instanciar un nuevo objeto Admin y establecer sus propiedades
        // según los valores en el mapa.
        // Por ejemplo:
        Persona admin = new Persona();
        Integer idPersonaInteger = (Integer) adminMap.get("idPersona");
        Long idPersonaLong = Long.valueOf(idPersonaInteger.longValue());
        admin.setIdPersona(idPersonaLong);
        admin.setNombre((String) adminMap.get("nombre"));
        admin.setApellidoP((String) adminMap.get("apellidoP"));
        admin.setApellidoM((String) adminMap.get("apellidoM"));
        admin.setTelefono((String) adminMap.get("telefono"));

        return admin;
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
        try{
            adminActualizado = adminBl.updateAdmin(admin);
            LOGGER.info("Admin actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el admin");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(adminActualizado);
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
