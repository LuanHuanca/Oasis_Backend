package com.ucb.SIS213.Oasis.api;

import java.util.List;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.ucb.SIS213.Oasis.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.ClienteBl;
import com.ucb.SIS213.Oasis.dto.LoginRequestDTO;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Cliente;
import com.ucb.SIS213.Oasis.exep.UserException;
 
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/cliente")
public class ClienteAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClienteAPI.class);

    ClienteBl clienteBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteAPI(ClienteBl clienteBl, JdbcTemplate jdbcTemplate) {
        this.clienteBl = clienteBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todos los clientes
    @GetMapping
    public ResponseDTO getAllClientes() {
        List<Cliente> clienteList;
        try{
            clienteList = clienteBl.getAllCliente();
            LOGGER.info("Clientes encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los clientes");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(clienteList);
    }

    // Endpoint para obtener un cliente por su correo
    @GetMapping("/correo/{correo}")
    public ResponseDTO getClienteByCorreo(@PathVariable String correo) {
        try {
            Cliente cliente = clienteBl.getClienteByCorreo(correo);
            if (cliente == null) {
                return new ResponseDTO("TASK-1001", "Cliente no encontrado con ese correo");
            }
            return new ResponseDTO(cliente);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener cliente por correo", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }



    // Endpoint para obtener un cliente por su id
    @GetMapping("/{id}")
    public ResponseDTO getClienteById(@PathVariable Long id) {
        Cliente cliente;
        try{
            cliente = clienteBl.getClienteById(id);
            LOGGER.info("Cliente encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el cliente");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(cliente);
    }

    // Endpoint para crear un cliente
    @PostMapping("/create")
    public ResponseDTO createCliente(@RequestBody Cliente cliente) {
        Cliente clienteCreado;
        try{
            clienteCreado = clienteBl.createCliente(cliente);
            LOGGER.info("Cliente creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el cliente", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(clienteCreado);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Cliente cliente;
        try {
            cliente = clienteBl.login(loginRequestDTO.getCorreo(), loginRequestDTO.getPassword());
            LOGGER.info("Se realizo la autencitacion");
        } catch (UserException e) {
            LOGGER.error("NO Se realizo la autencitacion", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(cliente);
    }

    @GetMapping("/clientes")
    public List<ClienteDTO> obtenerVuelos() {
        String sql = "select cl.*, pe.nombre, pe.apellidop, pe.apellidom, pe.telefono\n" +
                "from cliente cl, persona pe\n" +
                "where cl.persona_idpersona = pe.idpersona;";

        List<ClienteDTO> clientes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClienteDTO cliente = new ClienteDTO();
            cliente.setIdCliente(rs.getLong("idcliente"));
            cliente.setCorreo(rs.getString("correo"));
            cliente.setEstadoCuenta(rs.getBoolean("estadocuenta"));
            cliente.setIdPersona(rs.getLong("persona_idpersona"));

            cliente.setNombre(rs.getString("nombre"));
            cliente.setApellidoP(rs.getString("apellidop"));
            cliente.setApellidoM(rs.getString("apellidom"));
            cliente.setTelefono(rs.getString("telefono"));

            return cliente;
        });

        return clientes;
    }

    // Endpoint para modificar un cliente por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado;
        
        try{
            clienteActualizado = clienteBl.updateCliente(id, cliente);
            LOGGER.info("Cliente actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el cliente", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(clienteActualizado);
    }

    // Endpoint para eliminar un cliente
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteCliente(@PathVariable Long id) {
        try{
            clienteBl.deleteCliente(id);
            LOGGER.info("Cliente eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el cliente", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Cliente eliminado");
    }

    // Endpoint para cambiar solo la contraseña
    @PutMapping("/{id}/password")
    public ResponseDTO updatePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {

        try {
            String newPassword = body.get("password");
            if (newPassword == null || newPassword.isBlank()) {
                return new ResponseDTO("TASK-1002", "La contraseña no puede estar vacía");
            }
            Cliente clienteActualizado = clienteBl.updatePassword(id, newPassword);
            return new ResponseDTO(clienteActualizado);
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar la contraseña", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

}
