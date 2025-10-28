package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.HistorialContrasenaRepository;
import com.ucb.SIS213.Oasis.entity.HistorialContrasena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialContrasenaService {

    private final HistorialContrasenaRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HistorialContrasenaService(HistorialContrasenaRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Obtiene el historial de contraseñas para un cliente específico
     * @param idCliente ID del cliente
     * @return Lista de historial de contraseñas ordenada por fecha descendente
     */
    public List<HistorialContrasena> findHistoryForCliente(Long idCliente) {
        return repository.findByIdClienteOrderByFechaCambioDesc(idCliente);
    }

    /**
     * Obtiene el historial de contraseñas para un administrador específico
     * @param idAdmin ID del administrador
     * @return Lista de historial de contraseñas ordenada por fecha descendente
     */
    public List<HistorialContrasena> findHistoryForAdmin(Long idAdmin) {
        return repository.findByIdAdminOrderByFechaCambioDesc(idAdmin);
    }

    /**
     * Guarda el historial de contraseña para un cliente
     * @param idCliente ID del cliente
     * @param passwordHash Hash de la contraseña a guardar
     */
    public void saveHistory(Long idCliente, String passwordHash) {
        HistorialContrasena h = new HistorialContrasena();
        h.setIdCliente(idCliente);
        h.setIdAdmin(null); // Asegurar que sea null para cliente
        h.setContrasenaHash(passwordHash);
        h.setFechaCambio(LocalDateTime.now());
        repository.save(h);
    }

    /**
     * Guarda el historial de contraseña para un administrador
     * @param idAdmin ID del administrador
     * @param passwordHash Hash de la contraseña a guardar
     */
    public void saveHistoryForAdmin(Long idAdmin, String passwordHash) {
        HistorialContrasena h = new HistorialContrasena();
        h.setIdCliente(null); // Asegurar que sea null para admin
        h.setIdAdmin(idAdmin);
        h.setContrasenaHash(passwordHash);
        h.setFechaCambio(LocalDateTime.now());
        repository.save(h);
    }

    /**
     * Valida si una contraseña ya fue usada anteriormente por un cliente
     * @param idCliente ID del cliente
     * @param saltedPassword Contraseña con salt aplicado
     * @param checkLast Número de contraseñas anteriores a verificar
     * @return true si la contraseña ya fue usada, false en caso contrario
     */
    public boolean isPasswordUsedByCliente(Long idCliente, String saltedPassword, int checkLast) {
        List<HistorialContrasena> history = findHistoryForCliente(idCliente);
        int compared = 0;
        for (HistorialContrasena h : history) {
            if (compared >= checkLast) break;
            if (bCryptPasswordEncoder.matches(saltedPassword, h.getContrasenaHash())) {
                return true;
            }
            compared++;
        }
        return false;
    }

    /**
     * Valida si una contraseña ya fue usada anteriormente por un administrador
     * @param idAdmin ID del administrador
     * @param saltedPassword Contraseña con salt aplicado
     * @param checkLast Número de contraseñas anteriores a verificar
     * @return true si la contraseña ya fue usada, false en caso contrario
     */
    public boolean isPasswordUsedByAdmin(Long idAdmin, String saltedPassword, int checkLast) {
        List<HistorialContrasena> history = findHistoryForAdmin(idAdmin);
        int compared = 0;
        for (HistorialContrasena h : history) {
            if (compared >= checkLast) break;
            if (bCryptPasswordEncoder.matches(saltedPassword, h.getContrasenaHash())) {
                return true;
            }
            compared++;
        }
        return false;
    }
}
