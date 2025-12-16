package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long>{

    @Query(value = "SELECT * FROM admin WHERE idadmin = ?1",
            nativeQuery = true)
    List<Admin> findLabelsByUserId(Long userId);

    // Búsqueda case-insensitive del correo
    @Query(value = "SELECT * FROM admin WHERE LOWER(correo) = LOWER(?1)",
            nativeQuery = true)
    Admin findByCorreoIgnoreCase(String correo);
    
    // Mantener el método original para compatibilidad (deprecated - usar findByCorreoIgnoreCase)
    @Deprecated
    public Admin findByCorreo(String correo);
}
