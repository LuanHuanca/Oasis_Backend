package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.AlquilerAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerAutoDao extends JpaRepository<AlquilerAuto, Long>{

    @Query(value = "SELECT * FROM AlquilerAuto WHERE idAlquiler = ?1",
            nativeQuery = true)
    List<AlquilerAuto> findAlquilerAutoById(Long userId);

}
