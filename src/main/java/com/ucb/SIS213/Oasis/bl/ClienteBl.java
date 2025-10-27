package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ucb.SIS213.Oasis.dao.ClienteDao;
import com.ucb.SIS213.Oasis.entity.Cliente;
import com.ucb.SIS213.Oasis.exep.UserException;
import com.ucb.SIS213.Oasis.bl.HistorialContrasenaService;
import com.ucb.SIS213.Oasis.entity.HistorialContrasena;

import java.util.List;

@Service
public class ClienteBl {


    private ClienteDao clienteDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private HistorialContrasenaService historialService;

    @Autowired
    public ClienteBl(ClienteDao clienteDao, BCryptPasswordEncoder bCryptPasswordEncoder, HistorialContrasenaService historialService) {
        this.clienteDao = clienteDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.historialService = historialService;
    }

    public List<Cliente> getAllCliente() {
        return clienteDao.findAll();
    }

    public Cliente getClienteByCorreo(String correo) {
        Cliente cliente = clienteDao.findByCorreo(correo);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        return cliente;
    }

    public Cliente getClienteById(Long id) {
        Cliente cliente = clienteDao.findById(id).orElse(null);
        if (cliente == null) {
            throw new RuntimeException("Cliente does not exist");
        }
        return cliente;
    }

    public Cliente createCliente(Cliente cliente) {
        String password = cliente.getPassword() + "Aqm,24Dla";
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        cliente.setPassword(hashedPassword);
        System.out.println("Contraseña: " + cliente.getPassword());
        return clienteDao.save(cliente);
    }

    public Cliente login(String correo, String password) throws UserException {
        // Encontrar al cliente por correo
        Cliente cliente = clienteDao.findByCorreo(correo);
        
        if (cliente == null) {
            throw new UserException("Correo o contraseña incorrectos");
        }
        
        // Obtener la contraseña almacenada del cliente
        String hashedPassword = cliente.getPassword();
        String mypassword = password + "Aqm,24Dla";
        
        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada después de ser hasheada
        if (!bCryptPasswordEncoder.matches(mypassword, hashedPassword)) {
            throw new UserException("Correo o contraseña incorrectos");
        }
        
        // No es necesario volver a hashear la contraseña al hacer el login
        cliente.setPassword(null);
        
        return cliente;
    }
    
    public Cliente updateCliente(Long id, Cliente cliente) {
        Cliente clienteActual = clienteDao.findById(id).orElse(null);
            if (clienteActual == null) {
            throw new RuntimeException("Cliente does not exist");
        }

        clienteActual.setCorreo(cliente.getCorreo());
        clienteActual.setEstadoCuenta(cliente.getEstadoCuenta());
        clienteActual.setIdPersona(cliente.getIdPersona());

        // Actualizar contraseña si se envía una nueva
        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            String salted = cliente.getPassword() + "Aqm,24Dla";
            String hashed = bCryptPasswordEncoder.encode(salted);
            clienteActual.setPassword(hashed);
        }

        return clienteDao.save(clienteActual);

    }


    public Cliente updatePassword(Long id, String newPasswordRaw) {
        Cliente clienteActual = clienteDao.findById(id).orElse(null);
        if (clienteActual == null) {
            throw new RuntimeException("Cliente no existe");
        }

        String saltedNew = newPasswordRaw + "Aqm,24Dla";

        // Validar contra contraseña actual
        if (bCryptPasswordEncoder.matches(saltedNew, clienteActual.getPassword())) {
            throw new RuntimeException("La nueva contraseña no puede ser igual a la actual");
        }

        // Validar historial (últimas 5 contraseñas)
        int checkLast = 5;
        List<HistorialContrasena> history = historialService.findHistoryForPersona(clienteActual.getIdPersona());
        int compared = 0;
        for (HistorialContrasena h : history) {
            if (compared >= checkLast) break;
            if (bCryptPasswordEncoder.matches(saltedNew, h.getContrasenaHash())) {
                throw new RuntimeException("La nueva contraseña ya fue usada anteriormente. Elija otra.");
            }
            compared++;
        }

        // Guardar contraseña actual en historial
        historialService.saveHistory(clienteActual.getIdPersona(), clienteActual.getPassword());

        // Hashear y actualizar
        String hashed = bCryptPasswordEncoder.encode(saltedNew);
        clienteActual.setPassword(hashed);

        return clienteDao.save(clienteActual);

    }




    public void deleteCliente (Long id) {
        Cliente cliente = clienteDao.findById(id).orElse(null);
        if (cliente == null) {
            throw new RuntimeException("Cliente does not exist");
        }
        clienteDao.delete(cliente);
    }
}
