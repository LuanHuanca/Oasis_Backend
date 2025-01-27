package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.dto.ActividadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.ActividadBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Actividad;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/actividad")
public class ActividadAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ActividadAPI.class);

    ActividadBl actividadBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActividadAPI(ActividadBl actividadBl, JdbcTemplate jdbcTemplate) {
        this.actividadBl = actividadBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todas las actividades
    @GetMapping
    public ResponseDTO getAllActividades() {
        List<Actividad> actividadList;
        try{
            actividadList = actividadBl.getAllActividad();
            LOGGER.info("Actividades encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las actividades");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(actividadList);
    }

    // Endpoint para obtener una actividad por su id
    @GetMapping("/{id}")
    public ResponseDTO getActividadById(@PathVariable Long id) {
        Actividad actividad;
        try{
            actividad = actividadBl.getActividadById(id);
            LOGGER.info("Actividad encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la actividad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(actividad);
    }

    // Endpoint para crear una actividad
    @PostMapping("/create")
    public ResponseDTO createActividad(@RequestBody Actividad actividad) {
        Actividad actividadCreada;

        try{
            actividadCreada = actividadBl.createActividad(actividad);
            LOGGER.info("Actividad creada");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la actividad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(actividadCreada);
    }

    // Endpoint para actualizar una actividad
    @PutMapping("/update/{id}")
    public ResponseDTO updateActividadById(@PathVariable Long id, @RequestBody Actividad actividad) {
        Actividad actividadActualizada;
        try{
            actividadActualizada = actividadBl.updateActividadById(id, actividad);
            LOGGER.info("Actividad actualizada");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la actividad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(actividadActualizada);
    }

    // Endpoint para eliminar una actividad
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteActividad(@PathVariable Long id) {
        try{
            actividadBl.deleteActividadById(id);
            LOGGER.info("Actividad eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la actividad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Actividad eliminada");
    }

    @GetMapping("/actividades")
    public List<ActividadDTO> obtenerActividades() {
        String sql = "select ac.*, ca.categoria, cd.ciudad, p.pais\n" +
                "from actividad ac, categoriaatraccion ca, ciudad cd, pais p\n" +
                "where ac.categoriaactividad_idcatacti = ca.idcatatrac\n" +
                "and ac.ciudad_idciudad = cd.idciudad\n" +
                "and cd.pais_idpais = p.idpais;";

        List<ActividadDTO> actividades = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ActividadDTO actividad = new ActividadDTO();
            actividad.setIdActividad(rs.getLong("idactividad"));
            actividad.setActividad(rs.getString("actividad"));
            actividad.setIdCiudad(rs.getLong("ciudad_idciudad"));
            actividad.setIdCatActi(rs.getLong("categoriaactividad_idcatacti"));
            actividad.setFecha(rs.getDate("fecha"));
            actividad.setPrecio(rs.getDouble("precio"));
            actividad.setDetalle(rs.getString("detalle"));
            actividad.setCategoria(rs.getString("categoria"));
            actividad.setCiudad(rs.getString("ciudad"));
            actividad.setPais(rs.getString("pais"));

            return actividad;
        });

        return actividades;
    }
}
