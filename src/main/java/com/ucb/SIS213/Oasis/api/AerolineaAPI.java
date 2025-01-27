package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AerolineaBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Aerolinea;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/aerolinea")
public class AerolineaAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AerolineaAPI.class);

    AerolineaBl aerolineaBl;

    @Autowired
    public AerolineaAPI(AerolineaBl aerolineaBl) {
        this.aerolineaBl = aerolineaBl;
    }

    // Endpoint para obtener todas las aerolineas
    @GetMapping
    public ResponseDTO getAllAerolineas() {
        List<Aerolinea> aerolineaList;
        try{
            aerolineaList = aerolineaBl.getAllAerolinea();
            LOGGER.info("Aerolineas encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las aerolineas");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(aerolineaList);
    }

    // Endpoint para obtener una aerolinea por su id
    @GetMapping("/{id}")
    public ResponseDTO getAerolineaById(@PathVariable Long id) {
        Aerolinea aerolinea;
        try{
            aerolinea = aerolineaBl.getAerolineaById(id);
            LOGGER.info("Aerolinea encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la aerolinea");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(aerolinea);
    }

    // Endpoint para crear una aerolinea
    @PostMapping("/create")
    public ResponseDTO createAerolinea(@RequestBody Aerolinea aerolinea) {
        Aerolinea aerolineaCreada;
        try{
            aerolineaCreada = aerolineaBl.createAerolinea(aerolinea);
            LOGGER.info("Aerolinea creada");
            return new ResponseDTO(aerolineaCreada);
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la aerolinea");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para modificar una aerolinea por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateAerolinea(@PathVariable Long id, @RequestBody Aerolinea aerolinea) {
        Aerolinea aerolineaActualizada;
        try{
            aerolineaActualizada = aerolineaBl.updateAerolineaById(id, aerolinea);
            LOGGER.info("Aerolinea actualizada");
            return new ResponseDTO(aerolineaActualizada);
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la aerolinea");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para eliminar una aerolinea
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAerolinea(@PathVariable Long id) {
        try{
            aerolineaBl.deleteAerolineaById(id);
            LOGGER.info("Aerolinea eliminada");
            return new ResponseDTO("Aerolinea eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la aerolinea");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

}
