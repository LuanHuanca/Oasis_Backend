package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ucb.SIS213.Oasis.dao.HotelDao;
import com.ucb.SIS213.Oasis.entity.Hotel;

import java.util.List;

@Service
public class HotelBl {

    private final HotelDao hotelDao;

    @Autowired
    public HotelBl(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public List<Hotel> getAllHotels() {
        return hotelDao.findAll();
    }

    public Hotel getHotelById(Long id) {
        Hotel hotel = hotelDao.findById(id).orElse(null);
        if (hotel == null) {
            throw new RuntimeException("Hotel with id " + id + " does not exist");
        }
        return hotel;
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelDao.save(hotel);
    }

    public Hotel updateHotelById(Long id, Hotel hotel) {
        Hotel currentHotel = hotelDao.findById(id).orElse(null);
        if (currentHotel == null) {
            throw new RuntimeException("Hotel does not exist");
        }
        currentHotel.setHotel(hotel.getHotel());
        currentHotel.setPuntuacion(hotel.getPuntuacion());
        currentHotel.setIdCiudad(hotel.getIdCiudad());
        currentHotel.setIdCatHot(hotel.getIdCatHot());
        currentHotel.setTotalHabitaciones(hotel.getTotalHabitaciones());
        currentHotel.setDescripcion(hotel.getDescripcion());
        currentHotel.setImagenes(hotel.getImagenes());
        currentHotel.setUbicacion(hotel.getUbicacion());
        return hotelDao.save(currentHotel);
    }

    public void deleteHotelById(Long id) {
        Hotel hotel = hotelDao.findById(id).orElse(null);
        if (hotel == null) {
            throw new RuntimeException("Hotel does not exist");
        }
        hotelDao.delete(hotel);
    }
}
