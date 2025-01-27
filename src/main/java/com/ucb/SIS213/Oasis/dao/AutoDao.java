package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoDao extends JpaRepository<Auto, Long>{

    @Query(value = "SELECT * FROM auto WHERE idAuto = ?1",
            nativeQuery = true)
    List<Auto> findAutoById(Long userId);

    public Auto findByModelo(String modelo);
    public Auto findByMarca(String marca);
    public Auto findByTipo(String tipo);
}
