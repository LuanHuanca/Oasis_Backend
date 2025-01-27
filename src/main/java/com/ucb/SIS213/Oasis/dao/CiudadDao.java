package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CiudadDao extends JpaRepository<Ciudad, Long>{

    @Query(value = "SELECT * FROM ciudad WHERE idCiudad = ?1",
            nativeQuery = true)
    List<Ciudad> findCiudadById(Long userId);

    public Ciudad findByCiudad(String ciudad);
    public Ciudad findByIdPais(Long idPais);
    public Ciudad findByIdCiudad(Long idCiudad);

}
