package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.dto.AlquilerAutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AlquilerAutoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.AlquilerAuto;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/alquilerAuto")
public class AlquilerAutoAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AlquilerAutoAPI.class);

    AlquilerAutoBl alquilerAutoBl;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AlquilerAutoAPI(AlquilerAutoBl alquilerAutoBl, JdbcTemplate jdbcTemplate) {
        this.alquilerAutoBl = alquilerAutoBl;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint para obtener todos los alquileres de auto
    @GetMapping
    public ResponseDTO getAllAlquilerAuto() {
        List<AlquilerAuto> alquilerAutoList;
        try{
            alquilerAutoList = alquilerAutoBl.getAllAlquilerAuto();
            LOGGER.info("Alquileres de auto encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los alquileres de auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(alquilerAutoList);
    }

    // Endpoint para obtener un alquiler de auto por su id
    @GetMapping("/{id}")
    public ResponseDTO getAlquilerAutoById(@PathVariable Long id) {
        AlquilerAuto alquilerAuto;
        try{
            alquilerAuto = alquilerAutoBl.getAlquilerAutoById(id);
            LOGGER.info("Alquiler de auto encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el alquiler de auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(alquilerAuto);
    }

    // Endpoint para crear un alquiler de auto
    @PostMapping("/create")
    public ResponseDTO createAlquilerAuto(@RequestBody AlquilerAuto alquilerAuto) {
        AlquilerAuto alquilerAutoCreado;

        try{
            alquilerAutoCreado = alquilerAutoBl.createAlquilerAuto(alquilerAuto);
            LOGGER.info("Alquiler de auto creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el alquiler de auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(alquilerAutoCreado);
    }

    // Endpoint para actualizar un alquiler de auto por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateAlquilerAutoById(@PathVariable Long id, @RequestBody AlquilerAuto alquilerAuto) {
        AlquilerAuto alquilerAutoActualizado;
        try{
            alquilerAutoActualizado = alquilerAutoBl.updateAlquilerAutoById(id, alquilerAuto);
            LOGGER.info("Alquiler de auto actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el alquiler de auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(alquilerAutoActualizado);
    }

    // Endpoint para eliminar un alquiler de auto por su id
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAlquilerAutoById(@PathVariable Long id) {
        try{
            alquilerAutoBl.deleteAlquilerAutoById(id);
            LOGGER.info("Alquiler de auto eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el alquiler de auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Alquiler de auto eliminado");
    }

    @GetMapping("/alquierAutos")
    public List<AlquilerAutoDTO> obtenerVuelos() {
        String sql = "select aa.*, au.modelo, au.marca, au.tipo, c.ciudad, p.pais\n" +
                "from alquilerauto aa, auto au, ciudad c, pais p\n" +
                "where aa.auto_idauto = au.idauto\n" +
                "and aa.ciudad_idciudad = c.idciudad\n" +
                "and c.pais_idpais = p.idpais";

        List<AlquilerAutoDTO> alquilerAutos = jdbcTemplate.query(sql, (rs, rowNum) -> {
            AlquilerAutoDTO alquilerAuto = new AlquilerAutoDTO();
            alquilerAuto.setIdAlquiler(rs.getLong("idalquiler"));
            alquilerAuto.setPrecio(rs.getDouble("precio"));
            alquilerAuto.setDias(rs.getInt("dias"));
            alquilerAuto.setEmpresa(rs.getString("empresa"));
            alquilerAuto.setIdAuto(rs.getLong("auto_idauto"));
            alquilerAuto.setIdCiudad(rs.getLong("ciudad_idciudad"));
            alquilerAuto.setModelo(rs.getString("modelo"));
            alquilerAuto.setMarca(rs.getString("marca"));
            alquilerAuto.setTipo(rs.getString("tipo"));
            alquilerAuto.setCiudad(rs.getString("ciudad"));
            alquilerAuto.setPais(rs.getString("pais"));

            return alquilerAuto;
        });

        return alquilerAutos;
    }
}
