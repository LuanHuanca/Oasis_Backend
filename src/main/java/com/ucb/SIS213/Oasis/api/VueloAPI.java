package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.dto.VueloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.VueloBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Vuelo;
import com.ucb.SIS213.Oasis.exep.UserException;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/vuelo")
public class VueloAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(VueloAPI.class);

    VueloBl vueloBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VueloAPI(VueloBl vueloBl, JdbcTemplate jdbcTemplate) {
        this.vueloBl = vueloBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todos los vuelos
    @GetMapping
    public ResponseDTO getAllVuelos() {
        List<Vuelo> vueloList;
        try{
            vueloList = vueloBl.getAllVuelo();
            LOGGER.info("Vuelos encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los vuelos");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(vueloList);
    }

    // Endpoint para obtener un vuelo por su id
    @GetMapping("/{id}")
    public ResponseDTO getVueloById(@PathVariable Long id) {
        Vuelo vuelo;
        try{
            vuelo = vueloBl.getVueloById(id);
            LOGGER.info("Vuelo encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el vuelo");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(vuelo);
    }

    // Endpoint para crear un vuelo
    @PostMapping("/create")
    public ResponseDTO createVuelo(@RequestBody Vuelo vuelo) {
        Vuelo vueloCreado;
        try{
            vueloCreado = vueloBl.createVuelo(vuelo);
            LOGGER.info("Vuelo creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el vuelo");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(vueloCreado);
    }

    // Endpoint para modificar un vuelo por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateVuelo(@PathVariable Long id, @RequestBody Vuelo vuelo) {
        Vuelo vueloActualizado;
        try{
            vueloActualizado = vueloBl.updateVueloById(id, vuelo);
            LOGGER.info("Vuelo actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el vuelo");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(vueloActualizado);
    }

    // Endpoint para eliminar un vuelo
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteVuelo(@PathVariable Long id) {
        try{
            vueloBl.deleteVuelo(id);
            LOGGER.info("Vuelo eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el vuelo");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Vuelo eliminado");
    }

    @GetMapping("/vuelos")
    public List<VueloDTO> obtenerVuelos() {
        String sql = "SELECT v.*, co.ciudad AS ciudad_origen, cd.ciudad AS ciudad_destino, \n" +
                "       po.pais AS pais_origen, pd.pais AS pais_destino, ae.aerolinea\n" +
                "from vuelo v, ciudad co, ciudad cd, pais po, pais pd, aerolinea ae\n" +
                "where v.origen = co.idciudad\n" +
                "and v.destino = cd.idciudad\n" +
                "and co.pais_idpais = po.idpais\n" +
                "and cd.pais_idpais = pd.idpais\n" +
                "and v.aerolinea_idaerolinea = ae.idaerolinea;";

        List<VueloDTO> vuelos = jdbcTemplate.query(sql, (rs, rowNum) -> {
            VueloDTO vuelo = new VueloDTO();
            vuelo.setIdViaje(rs.getLong("idviaje"));
            vuelo.setOrigen(rs.getLong("origen"));
            vuelo.setDestino(rs.getLong("destino"));
            vuelo.setIdAerolinea(rs.getLong("aerolinea_idaerolinea"));
            vuelo.setFechaInicio(rs.getDate("fechainicio"));
            vuelo.setFechaFin(rs.getDate("fechafin"));
            vuelo.setPrecio(rs.getDouble("precio"));
            vuelo.setPersonas(rs.getLong("personas"));
            vuelo.setNroReserva(rs.getString("nroreserva"));

            vuelo.setCiudad_origen(rs.getString("ciudad_origen"));
            vuelo.setCiudad_destino(rs.getString("ciudad_destino"));
            vuelo.setPais_origen(rs.getString("pais_origen"));
            vuelo.setPais_destino(rs.getString("pais_destino"));
            vuelo.setAerolinea(rs.getString("aerolinea"));
            return vuelo;
        });

        return vuelos;
    }
}
