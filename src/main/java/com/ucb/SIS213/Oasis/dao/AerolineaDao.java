package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AerolineaDao extends JpaRepository<Aerolinea, Long>{

    @Query(value = "SELECT * FROM Aerolinea WHERE idAerolinea = ?1",
            nativeQuery = true)
    List<Aerolinea> findAerolineaById(Long userId);

    public Aerolinea findByAerolinea(String nombre);

}
