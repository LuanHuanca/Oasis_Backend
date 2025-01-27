package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturacionDao extends JpaRepository<Facturacion, Long>{

    @Query(value = "SELECT * FROM Facturacion WHERE idFactura = ?1",
            nativeQuery = true)
    List<Facturacion> findFacturacionById(Long userId);

    public Facturacion findByNIT(String nit);

    @Query(value = "SELECT MAX(idFactura) FROM Facturacion;", nativeQuery = true)
    Long getLastInsertedPersonaId();
}
