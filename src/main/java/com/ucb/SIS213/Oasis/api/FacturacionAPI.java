package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.FacturacionBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Facturacion;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/facturacion")
public class FacturacionAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FacturacionAPI.class);

    FacturacionBl facturacionBl;

    @Autowired
    public FacturacionAPI(FacturacionBl facturacionBl) {
        this.facturacionBl = facturacionBl;
    }

    // Endpoint para obtener todas las facturaciones
    @GetMapping
    public ResponseDTO getAllFacturacion() {
        List<Facturacion> facturacionList;
        try{
            facturacionList = facturacionBl.getAllFacturacion();
            LOGGER.info("Facturaciones encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las facturaciones");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(facturacionList);
    }

    // Endpoint para obtener una facturacion por su id
    @GetMapping("/{id}")
    public ResponseDTO getFacturacionById(@PathVariable Long id) {
        Facturacion facturacion;
        try{
            facturacion = facturacionBl.getFacturacionById(id);
            LOGGER.info("Facturacion encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la facturacion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(facturacion);
    }

    // Endpoint para crear una facturacion
    @PostMapping("/create")
    public ResponseDTO createFacturacion(@RequestBody Facturacion facturacion) {
        Facturacion facturacionCreada;
        try{
            facturacionCreada = facturacionBl.createFacturacion(facturacion);
            LOGGER.info("Facturacion creada");
            return new ResponseDTO(facturacionCreada);
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la facturacion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para modificar una facturacion por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateFacturacion(@PathVariable Long id, @RequestBody Facturacion facturacion) {
        Facturacion facturacionActualizada;
        try{
            facturacionActualizada = facturacionBl.updateFacturacionById(id, facturacion);
            LOGGER.info("Facturacion actualizada");
            return new ResponseDTO(facturacionActualizada);
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la facturacion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para eliminar una facturacion
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteFacturacion(@PathVariable Long id) {
        try{
            facturacionBl.deleteFacturacionById(id);
            LOGGER.info("Facturacion eliminada");
            return new ResponseDTO("Facturacion eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la facturacion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para consegir el id de la ultima facturacion
    @GetMapping("/lastId")
    public ResponseDTO getLastInsertedFacturacionId() {
        Long id;
        try{
            id = facturacionBl.getLastIdFacturacion();
            LOGGER.info("Id de la ultima facturacion encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el id de la ultima facturacion");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(id);
    }

}
