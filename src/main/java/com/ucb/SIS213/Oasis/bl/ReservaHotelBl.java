package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.ReservaHotelDao;
import com.ucb.SIS213.Oasis.entity.ReservaHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaHotelBl {

    private final ReservaHotelDao reservaHotelDao;

    @Autowired
    public ReservaHotelBl(ReservaHotelDao reservaHotelDao) {
        this.reservaHotelDao = reservaHotelDao;
    }

    public List<ReservaHotel> getAllReservas() {
        return reservaHotelDao.findAll();
    }

    public ReservaHotel getReservaById(Long id) {
        return reservaHotelDao.findById(id).orElseThrow(() -> new RuntimeException("ReservaHotel does not exist"));
    }

    public ReservaHotel createReserva(ReservaHotel reservaHotel) {
        return reservaHotelDao.save(reservaHotel);
    }

    public ReservaHotel updateReservaById(Long id, ReservaHotel reservaHotel) {
        ReservaHotel currentReservaHotel = reservaHotelDao.findById(id).orElseThrow(() -> new RuntimeException("ReservaHotel does not exist"));
        currentReservaHotel.setFechaInicio(reservaHotel.getFechaInicio());
        currentReservaHotel.setFechaFin(reservaHotel.getFechaFin());
        currentReservaHotel.setPrecio(reservaHotel.getPrecio());
        currentReservaHotel.setPersonas(reservaHotel.getPersonas());
        currentReservaHotel.setIdHotel(reservaHotel.getIdHotel());
        currentReservaHotel.setNroReservaHotel(reservaHotel.getNroReservaHotel());
        currentReservaHotel.setHabitaciones(reservaHotel.getHabitaciones());
        currentReservaHotel.setDetalle(reservaHotel.getDetalle());
        return reservaHotelDao.save(currentReservaHotel);
    }

    public void deleteReservaById(Long id) {
        ReservaHotel reservaHotel = reservaHotelDao.findById(id).orElseThrow(() -> new RuntimeException("ReservaHotel does not exist"));
        reservaHotelDao.delete(reservaHotel);
    }
}
