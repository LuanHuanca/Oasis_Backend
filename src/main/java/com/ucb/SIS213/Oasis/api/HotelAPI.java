package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.HotelBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Hotel;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/hotel")
public class HotelAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HotelAPI.class);

    private final HotelBl hotelBl;

    @Autowired
    public HotelAPI(HotelBl hotelBl) {
        this.hotelBl = hotelBl;
    }

    // Endpoint to get all hotels
    @GetMapping
    public ResponseDTO getAllHotels() {
        List<Hotel> hotelList;
        try {
            hotelList = hotelBl.getAllHotels();
            LOGGER.info("Hotels found");
        } catch (RuntimeException e) {
            LOGGER.error("Error fetching hotels", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(hotelList);
    }

    @GetMapping("/{id}")
    public ResponseDTO getHotelById(@PathVariable("id") Long id) {
        Hotel hotel;
        try {
            hotel = hotelBl.getHotelById(id);
            LOGGER.info("Hotel found: {}", hotel);
        } catch (Exception e) {
            LOGGER.error("Error fetching hotel with id: {}", id, e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(hotel);
    }

    // Endpoint to create a hotel
    @PostMapping("/create")
    public ResponseDTO createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel;
        try {
            createdHotel = hotelBl.createHotel(hotel);
            LOGGER.info("Hotel created");
        } catch (RuntimeException e) {
            LOGGER.error("Error creating hotel", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(createdHotel);
    }

    // Endpoint to update a hotel
    @PutMapping("/update/{id}")
    public ResponseDTO updateHotelById(@PathVariable Long id, @RequestBody Hotel hotel) {
        Hotel updatedHotel;
        try {
            updatedHotel = hotelBl.updateHotelById(id, hotel);
            LOGGER.info("Hotel updated");
        } catch (RuntimeException e) {
            LOGGER.error("Error updating hotel", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(updatedHotel);
    }

    // Endpoint to delete a hotel
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteHotel(@PathVariable Long id) {
        try {
            hotelBl.deleteHotelById(id);
            LOGGER.info("Hotel deleted");
        } catch (RuntimeException e) {
            LOGGER.error("Error deleting hotel", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Hotel deleted");
    }
}
