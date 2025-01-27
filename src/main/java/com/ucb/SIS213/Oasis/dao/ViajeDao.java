package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeDao extends JpaRepository<Viaje, Long>{

    @Query(value = "SELECT * FROM Viaje WHERE idViaje = ?1",
            nativeQuery = true)
    List<Viaje> findViajeById(Long userId);

    @Query(value = "SELECT MAX(idReservaViaja) FROM ReservaViaje;", nativeQuery = true)
    Long getLastIdViaje();

}
