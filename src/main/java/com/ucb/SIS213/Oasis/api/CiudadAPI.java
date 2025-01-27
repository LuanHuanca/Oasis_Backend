package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.CiudadBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Ciudad;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/ciudad")
public class CiudadAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CiudadAPI.class);

    CiudadBl ciudadBl;

    @Autowired
    public CiudadAPI(CiudadBl ciudadBl) {
        this.ciudadBl = ciudadBl;
    }

    // Endpoint para obtener todas las ciudades
    @GetMapping
    public ResponseDTO getAllCiudades() {
        List<Ciudad> ciudadList;
        try{
            ciudadList = ciudadBl.getAllCiudad();
            LOGGER.info("Ciudades encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las ciudades");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(ciudadList);
    }

    // Endpoint para obtener una ciudad por su id
    @GetMapping("/{id}")
    public ResponseDTO getCiudadById(@PathVariable Long id) {
        Ciudad ciudad;
        try{
            ciudad = ciudadBl.getCiudadById(id);
            LOGGER.info("Ciudad encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la ciudad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(ciudad);
    }

    // Endpoint para crear una ciudad
    @PostMapping("/create")
    public ResponseDTO createCiudad(@RequestBody Ciudad ciudad) {
        Ciudad ciudadCreada;
        try{
            ciudadCreada = ciudadBl.createCiudad(ciudad);
            LOGGER.info("Ciudad creada");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la ciudad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(ciudadCreada);
    }

    // Endpoint para actualizar una ciudad por id
    @PutMapping("/update/{id}")
    public ResponseDTO updateCiudad(@PathVariable Long id, @RequestBody Ciudad ciudad) {
        Ciudad ciudadActualizada;
        try{
            ciudadActualizada = ciudadBl.updateCiudadById(id, ciudad);
            LOGGER.info("Ciudad actualizada");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la ciudad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(ciudadActualizada);
    }



    // Endpoint para eliminar una ciudad
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteCiudad(@PathVariable Long id) {
        try{
            ciudadBl.deleteCiudadById(id);
            LOGGER.info("Ciudad eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la ciudad");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Ciudad eliminada");
    }
}
