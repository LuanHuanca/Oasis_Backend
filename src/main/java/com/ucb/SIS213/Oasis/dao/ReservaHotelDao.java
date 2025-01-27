package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.ReservaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaHotelDao extends JpaRepository<ReservaHotel, Long> {
}
