package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ucb.SIS213.Oasis.dao.ClienteDao;
import com.ucb.SIS213.Oasis.entity.Cliente;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.List;

@Service
public class ClienteBl {


    private ClienteDao clienteDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ClienteBl(ClienteDao clienteDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clienteDao = clienteDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Cliente> getAllCliente() {
        return clienteDao.findAll();
    }

    public Cliente getClienteById(Long id) {
        Cliente cliente = clienteDao.findById(id).orElse(null);
        if (cliente == null) {
            throw new RuntimeException("Cliente does not exist");
        }
        return cliente;
    }

    public Cliente createCliente(Cliente cliente) {
        String password = cliente.getPassword();
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
        
        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada después de ser hasheada
        if (!bCryptPasswordEncoder.matches(password, hashedPassword)) {
            throw new UserException("Correo o contraseña incorrectos");
        }
        
        // No es necesario volver a hashear la contraseña al hacer el login
        cliente.setPassword(null);
        
        return cliente;
    }
    

    public Cliente updateCliente(Long id,Cliente cliente) {
        Cliente clienteActual = clienteDao.findById(id).orElse(null);
        if (clienteActual == null) {
            throw new RuntimeException("Cliente does not exist");
        }
        clienteActual.setCorreo(cliente.getCorreo());
        clienteActual.setPassword(cliente.getPassword());
        clienteActual.setEstadoCuenta(cliente.getEstadoCuenta());
        clienteActual.setIdPersona(cliente.getIdPersona());
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
