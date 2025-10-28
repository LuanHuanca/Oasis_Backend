package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.HistorialContrasena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialContrasenaRepository extends JpaRepository<HistorialContrasena, Long> {
    List<HistorialContrasena> findByIdClienteOrderByFechaCambioDesc(Long idCliente);
    List<HistorialContrasena> findByIdAdminOrderByFechaCambioDesc(Long idAdmin);
}
