package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroDao extends JpaRepository<Seguro, Long>{

    @Query(value = "SELECT * FROM Seguro WHERE idSeguro = ?1",
            nativeQuery = true)
    List<Seguro> findSeguroById(Long userId);
}
