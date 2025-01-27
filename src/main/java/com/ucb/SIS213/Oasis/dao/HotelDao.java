package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelDao extends JpaRepository<Hotel, Long> {

    @Query(value = "SELECT * FROM Hotel WHERE idHotel = ?1", nativeQuery = true)
    List<Hotel> findHotelById(Long idHotel);
}
