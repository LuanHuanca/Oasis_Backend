package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDao extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM persona WHERE idPersona = ?1",
            nativeQuery = true)
    List<Cliente> findLabelsByUserId(Long userId);

    // Búsqueda case-insensitive del correo
    @Query(value = "SELECT * FROM cliente WHERE LOWER(correo) = LOWER(?1)",
            nativeQuery = true)
    Cliente findByCorreoIgnoreCase(String correo);
    
    // Mantener el método original para compatibilidad (deprecated - usar findByCorreoIgnoreCase)
    @Deprecated
    public Cliente findByCorreo(String correo);


}
