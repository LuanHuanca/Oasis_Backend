package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.ReservaHotelBl;
import com.ucb.SIS213.Oasis.dto.ReservaHotelDTO;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.ReservaHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/reservahotel")
public class ReservaHotelAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ReservaHotelAPI.class);

    private final ReservaHotelBl reservaHotelBl;

    @Autowired
    public ReservaHotelAPI(ReservaHotelBl reservaHotelBl, JdbcTemplate jdbcTemplate) {
        this.reservaHotelBl = reservaHotelBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;

    // Endpoint to get all reservas
    @GetMapping
    public ResponseDTO getAllReservas() {
        List<ReservaHotel> reservaHotelList;
        try {
            reservaHotelList = reservaHotelBl.getAllReservas();
            LOGGER.info("Reservas found");
        } catch (RuntimeException e) {
            LOGGER.error("Error fetching reservas", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(reservaHotelList);
    }

    // Endpoint to get a reserva by id
    @GetMapping("/{id}")
    public ResponseDTO getReservaById(@PathVariable("id") Long id) {
        ReservaHotel reservaHotel;
        try {
            reservaHotel = reservaHotelBl.getReservaById(id);
            LOGGER.info("Reserva found");
        } catch (RuntimeException e) {
            LOGGER.error("Error fetching reserva", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(reservaHotel);
    }

    // Endpoint to create a reserva
    @PostMapping("/create")
    public ResponseDTO createReserva(@RequestBody ReservaHotel reservaHotel) {
        ReservaHotel createdReservaHotel;
        try {
            createdReservaHotel = reservaHotelBl.createReserva(reservaHotel);
            LOGGER.info("Reserva created");
        } catch (RuntimeException e) {
            LOGGER.error("Error creating reserva", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(createdReservaHotel);
    }

    // Endpoint to update a reserva
    @PutMapping("/update/{id}")
    public ResponseDTO updateReservaById(@PathVariable("id") Long id, @RequestBody ReservaHotel reservaHotel) {
        ReservaHotel updatedReservaHotel;
        try {
            updatedReservaHotel = reservaHotelBl.updateReservaById(id, reservaHotel);
            LOGGER.info("Reserva updated");
        } catch (RuntimeException e) {
            LOGGER.error("Error updating reserva", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(updatedReservaHotel);
    }

    // Endpoint to delete a reserva
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteReserva(@PathVariable("id") Long id) {
        try {
            reservaHotelBl.deleteReservaById(id);
            LOGGER.info("Reserva deleted");
        } catch (RuntimeException e) {
            LOGGER.error("Error deleting reserva", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Reserva deleted");
    }

    @GetMapping("/reservas")
    public List<ReservaHotelDTO> obtenerReservasHotel() {
        String sql = "SELECT rh.*, h.hotel, c.ciudad, p.pais " +
                "FROM reservahotel rh, hotel h, ciudad c, pais p " +
                "WHERE rh.hotel_idhotel = h.idhotel " +
                "AND h.ciudad_idciudad = c.idciudad " +
                "AND c.pais_idpais = p.idpais";

        List<ReservaHotelDTO> reservas = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservaHotelDTO reserva = new ReservaHotelDTO();
            reserva.setIdReservaHotel(rs.getLong("idreservahotel"));
            reserva.setFechaInicio(rs.getDate("fechainicio"));
            reserva.setFechaFin(rs.getDate("fechafin"));
            reserva.setPrecio(rs.getBigDecimal("precio"));
            reserva.setPersonas(rs.getInt("personas"));
            reserva.setIdHotel(rs.getInt("hotel_idhotel"));
            reserva.setNroReservaHotel(rs.getString("nroreservahotel"));
            reserva.setHabitaciones(rs.getInt("habitaciones"));
            reserva.setDetalle(rs.getString("detalle"));
            reserva.setHotel(rs.getString("hotel"));
            reserva.setCiudad(rs.getString("ciudad"));
            reserva.setPais(rs.getString("pais"));
            return reserva;
        });

        return reservas;
    }
}
