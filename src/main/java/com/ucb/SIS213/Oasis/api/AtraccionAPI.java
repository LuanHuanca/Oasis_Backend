package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.dto.AtraccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AtraccionBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Atraccion;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/atraccion")
public class AtraccionAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AtraccionAPI.class);

    AtraccionBl atraccionBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AtraccionAPI(AtraccionBl atraccionBl, JdbcTemplate jdbcTemplate) {
        this.atraccionBl = atraccionBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todas las atracciones
    @GetMapping
    public ResponseDTO getAllAtracciones() {
        List<Atraccion> atraccionList;
        try{
            atraccionList = atraccionBl.getAllAtraccion();
            LOGGER.info("Atracciones encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las atracciones");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(atraccionList);
    }

    // Endpoint para obtener una atraccion por su id
    @GetMapping("/{id}")
    public ResponseDTO getAtraccionById(@PathVariable Long id) {
        Atraccion atraccion;
        try{
            atraccion = atraccionBl.getAtraccionById(id);
            LOGGER.info("Atraccion encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la atraccion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(atraccion);
    }

    // Endpoint para crear una atraccion
    @PostMapping("/create")
    public ResponseDTO createAtraccion(@RequestBody Atraccion atraccion) {
        Atraccion atraccionCreada;

        try{
            atraccionCreada = atraccionBl.createAtraccion(atraccion);
            LOGGER.info("Atraccion creada");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la atraccion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(atraccionCreada);
    }

    // Endpoint para actualizar una atraccion
    @PutMapping("/update/{id}")
    public ResponseDTO updateAtraccionById(@PathVariable Long id, @RequestBody Atraccion atraccion) {
        Atraccion atraccionActualizada;

        try {
            atraccionActualizada = atraccionBl.updateAtraccionById(id, atraccion);
            LOGGER.info("Atraccion actualizada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar la atraccion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(atraccionActualizada);
    }

    // Endpoint para eliminar una atraccion
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAtraccionById(@PathVariable Long id) {
        try {
            atraccionBl.deleteAtraccionById(id);
            LOGGER.info("Atraccion eliminada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar la atraccion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Atraccion eliminada");
    }

    @GetMapping("/atracciones")
    public List<AtraccionDTO> obtenerVuelos() {
        String sql = "select at.*, ca.categoria, cd.ciudad, p.pais\n" +
                "from atraccion at, categoriaatraccion ca, ciudad cd, pais p\n" +
                "where at.categoriaatraccion_idcatatrac = ca.idcatatrac\n" +
                "and at.ciudad_idciudad = cd.idciudad\n" +
                "and cd.pais_idpais = p.idpais;";

        List<AtraccionDTO> atracciones = jdbcTemplate.query(sql, (rs, rowNum) -> {
            AtraccionDTO atraccion = new AtraccionDTO();
            atraccion.setIdAtraccion(rs.getLong("idatraccion"));
            atraccion.setAtraccion(rs.getString("atraccion"));
            atraccion.setIdCatAtrac(rs.getLong("categoriaatraccion_idcatatrac"));
            atraccion.setIdCiudad(rs.getLong("ciudad_idciudad"));
            atraccion.setPrecio(rs.getDouble("precio"));
            atraccion.setDetalle(rs.getString("detalle"));
            atraccion.setCategoria(rs.getString("categoria"));
            atraccion.setCiudad(rs.getString("ciudad"));
            atraccion.setPais(rs.getString("pais"));

            return atraccion;
        });

        return atracciones;
    }
}
