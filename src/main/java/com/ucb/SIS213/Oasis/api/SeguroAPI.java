package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.SeguroBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Seguro;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/seguro")
public class SeguroAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SeguroAPI.class);

    SeguroBl seguroBl;

    @Autowired
    public SeguroAPI(SeguroBl seguroBl) {
        this.seguroBl = seguroBl;
    }

    // Endpoint para obtener todos los seguros
    @GetMapping
    public ResponseDTO getAllSeguros() {
        List<Seguro> seguroList;
        try{
            seguroList = seguroBl.getAllSeguro();
            LOGGER.info("Seguros encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los seguros");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(seguroList);
    }

    // Endpoint para obtener un seguro por su id
    @GetMapping("/{id}")
    public ResponseDTO getSeguroById(@PathVariable Long id) {
        Seguro seguro;
        try{
            seguro = seguroBl.getSeguroById(id);
            LOGGER.info("Seguro encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el seguro");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(seguro);
    }

    // Endpoint para crear un seguro
    @PostMapping("/create")
    public ResponseDTO createSeguro(@RequestBody Seguro seguro) {
        Seguro seguroCreado;

        try{
            seguroCreado = seguroBl.createSeguro(seguro);
            LOGGER.info("Seguro creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el seguro");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(seguroCreado);
    }

    // Endpoint para actualizar un seguro por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateSeguroById(@PathVariable Long id, @RequestBody Seguro seguro) {
        Seguro seguroActualizado;

        try{
            seguroActualizado = seguroBl.updateSeguroById(id, seguro);
            LOGGER.info("Seguro actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el seguro");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(seguroActualizado);
    }

    // Endpoint para eliminar un seguro por su id
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteSeguroById(@PathVariable Long id) {
        try{
            seguroBl.deleteSeguroById(id);
            LOGGER.info("Seguro eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el seguro");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Seguro eliminado");
    }
}
