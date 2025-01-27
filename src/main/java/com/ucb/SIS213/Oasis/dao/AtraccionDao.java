package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Atraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtraccionDao extends JpaRepository<Atraccion, Long>{

    @Query(value = "SELECT * FROM Atraccion WHERE idAtraccion = ?1",
            nativeQuery = true)
    List<Atraccion> findAtraccionById(Long userId);
}
