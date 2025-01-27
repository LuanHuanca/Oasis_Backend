package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.dto.ReservaViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.ViajeBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Viaje;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/viaje")
public class ViajeAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ViajeAPI.class);

    ViajeBl viajeBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ViajeAPI(ViajeBl viajeBl, JdbcTemplate jdbcTemplate) {
        this.viajeBl = viajeBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todos los viajes
    @GetMapping
    public ResponseDTO getAllViajes() {
        List<Viaje> viajeList;
        try{
            viajeList = viajeBl.getAllViaje();
            LOGGER.info("Viajes encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los viajes");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(viajeList);
    }

    // Endpoint para obtener un viaje por su id
    @GetMapping("/{id}")
    public ResponseDTO getViajeById(@PathVariable Long id) {
        Viaje viaje;
        try{
            viaje = viajeBl.getViajeById(id);
            LOGGER.info("Viaje encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el viaje");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(viaje);
    }

    // Endpoint para crear un viaje
    @PostMapping("/create")
    public ResponseDTO createViaje(@RequestBody Viaje viaje) {
        Viaje viajeCreado;

        try{
            viajeCreado = viajeBl.createViaje(viaje);
            LOGGER.info("Viaje creado");
            return new ResponseDTO(viajeCreado);
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el viaje");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para modificar un viaje por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateViaje(@PathVariable Long id, @RequestBody Viaje viaje) {
        Viaje viajeActualizado;
        try{
            viajeActualizado = viajeBl.updateViajeById(id, viaje);
            LOGGER.info("Viaje actualizado");
            return new ResponseDTO(viajeActualizado);
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el viaje");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para eliminar un viaje
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteViaje(@PathVariable Long id) {
        try{
            viajeBl.deleteViajeById(id);
            LOGGER.info("Viaje eliminado");
            return new ResponseDTO("Viaje eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el viaje");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }


    @GetMapping("/viajes")
    public List<ReservaViajeDTO> obtenerViajes() {
        String sql = "SELECT \n" +
                "    rv.*,\n" +
                "    co.ciudad AS ciudadOrigen,\n" +
                "    po.pais AS paisOrigen,\n" +
                "    cd.ciudad AS ciudadDestino,\n" +
                "    pd.pais AS paisDestino,\n" +
                "    p.Nombre AS nombrePersona,\n" +
                "    p.ApellidoP AS apellidoPaterno,\n" +
                "    p.ApellidoM AS apellidoMaterno,\n" +
                "    cl.correo AS correoPersona,\n" +
                "    v.fechaInicio AS fechaInicioViaje,\n" +
                "    v.fechaFin AS fechaFinViaje\n" +
                "FROM \n" +
                "    ReservaViaje rv\n" +
                "JOIN \n" +
                "    Vuelo v ON rv.Viaje_idViaje = v.idViaje\n" +
                "JOIN \n" +
                "    Ciudad co ON v.origen = co.idCiudad\n" +
                "JOIN \n" +
                "    Pais po ON co.Pais_idPais = po.idPais\n" +
                "JOIN \n" +
                "    Ciudad cd ON v.destino = cd.idCiudad\n" +
                "JOIN \n" +
                "    Pais pd ON cd.Pais_idPais = pd.idPais\n" +
                "JOIN \n" +
                "    Cliente cl ON rv.Cliente_idCliente = cl.idCliente\n" +
                "JOIN \n" +
                "    Persona p ON cl.Persona_idPersona = p.idPersona\n" +
                "ORDER BY \n" +
                "    rv.fecha;";

        List<ReservaViajeDTO> viajes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservaViajeDTO viaje = new ReservaViajeDTO();
            viaje.setIdReservaViaje(rs.getLong("idReservaViaja"));
            viaje.setFecha(rs.getDate("fecha"));
            viaje.setIdCliente(rs.getLong("Cliente_idCliente"));
            viaje.setIdViaje(rs.getLong("Viaje_idViaje"));
            viaje.setIdSeguro(rs.getLong("Seguro_idSeguro"));
            viaje.setIdAlquiler(rs.getLong("AlquilerAuto_idAlquiler"));
            viaje.setIdAtraccion(rs.getLong("Atraccion_idAtraccion"));
            viaje.setIdActividad(rs.getLong("Actividad_idActividad"));
            viaje.setIdReservaHotel(rs.getLong("ReservaHotel_idReservaHotel"));

            viaje.setCiudadOrigen(rs.getString("ciudadOrigen"));
            viaje.setCiudadDestino(rs.getString("ciudadDestino"));
            viaje.setPaisOrigen(rs.getString("paisOrigen"));
            viaje.setPaisDestino(rs.getString("paisDestino"));
            viaje.setNombrePersona(rs.getString("nombrePersona"));
            viaje.setApellidoPaterno(rs.getString("apellidoPaterno"));
            viaje.setApellidoMaterno(rs.getString("apellidoMaterno"));
            viaje.setCorreoPersona(rs.getString("correoPersona"));
            viaje.setFechaInicioViaje(rs.getDate("fechaInicioViaje"));
            viaje.setFechaFinViaje(rs.getDate("fechaFinViaje"));
            return viaje;
        });

        return viajes;
    }

    //Endpoint para obtener el último id de un viaje
    @GetMapping("/lastId")
    public ResponseDTO getLastInsertedViajeId() {
        Long id;
        try{
            id = viajeBl.getLastIdViaje();
            LOGGER.info("Id del último viaje encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el id del último viaje");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(id);
    }
}
