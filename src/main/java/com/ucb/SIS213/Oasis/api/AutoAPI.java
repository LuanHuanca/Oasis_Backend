package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AutoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Auto;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/auto")
public class AutoAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AutoAPI.class);

    AutoBl autoBl;

    @Autowired
    public AutoAPI(AutoBl autoBl) {
        this.autoBl = autoBl;
    }

    // Endpoint para obtener todos los autos
    @GetMapping
    public ResponseDTO getAllAutos() {
        List<Auto> autoList;
        try{
            autoList = autoBl.getAllAuto();
            LOGGER.info("Autos encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los autos");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(autoList);
    }

    // Endpoint para obtener un auto por su id
    @GetMapping("/{id}")
    public ResponseDTO getAutoById(@PathVariable Long id) {
        Auto auto;
        try{
            auto = autoBl.getAutoById(id);
            LOGGER.info("Auto encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(auto);
    }

    // Endpoint para crear un auto
    @PostMapping("/create")
    public ResponseDTO createAuto(@RequestBody Auto auto) {
        Auto autoCreado;
        try{
            autoCreado = autoBl.createAuto(auto);
            LOGGER.info("Auto creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(autoCreado);
    }

    // Endpoint para actualizar un auto por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateAutoById(@PathVariable Long id, @RequestBody Auto auto) {
        Auto autoActualizado;
        try{
            autoActualizado = autoBl.updateAutoById(id, auto);
            LOGGER.info("Auto actualizado");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(autoActualizado);
    }

    // Endpoint para eliminar un auto por su id
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteAutoById(@PathVariable Long id) {
        try {
            autoBl.deleteAutoById(id);
            LOGGER.info("Auto eliminado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar el auto");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Auto Eliminado");
    }
}
