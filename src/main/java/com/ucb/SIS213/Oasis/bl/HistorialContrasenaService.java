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

    public List<HistorialContrasena> findHistoryForPersona(Long idPersona) {
        return repository.findByIdPersonaOrderByFechaCambioDesc(idPersona);
    }

    public void saveHistory(Long idPersona, String passwordHash) {
        HistorialContrasena h = new HistorialContrasena();
        h.setIdPersona(idPersona);
        h.setContrasenaHash(passwordHash);
        h.setFechaCambio(LocalDateTime.now());
        repository.save(h);
    }
}
