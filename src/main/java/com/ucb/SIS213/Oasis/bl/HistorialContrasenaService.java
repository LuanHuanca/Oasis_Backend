package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.HistorialContrasenaRepository;
import com.ucb.SIS213.Oasis.entity.HistorialContrasena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialContrasenaService {

    private final HistorialContrasenaRepository repository;

    @Autowired
    public HistorialContrasenaService(HistorialContrasenaRepository repository) {
        this.repository = repository;
    }

    public List<HistorialContrasena> findHistoryForCliente(Long idCliente) {
        return repository.findByIdClienteOrderByFechaCambioDesc(idCliente);
    }

    public List<HistorialContrasena> findHistoryForAdmin(Long idAdmin) {
        return repository.findByIdAdminOrderByFechaCambioDesc(idAdmin);
    }

    public void saveHistory(Long idCliente, String passwordHash) {
        HistorialContrasena h = new HistorialContrasena();
        h.setIdCliente(idCliente);
        h.setContrasenaHash(passwordHash);
        h.setFechaCambio(LocalDateTime.now());
        repository.save(h);
    }

    public void saveHistoryForAdmin(Long idAdmin, String passwordHash) {
        HistorialContrasena h = new HistorialContrasena();
        h.setIdAdmin(idAdmin);
        h.setContrasenaHash(passwordHash);
        h.setFechaCambio(LocalDateTime.now());
        repository.save(h);
    }
}
