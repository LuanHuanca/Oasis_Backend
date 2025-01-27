package com.ucb.SIS213.Oasis.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.PersonaBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Persona;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/persona")
public class PersonaAPI {
 
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PersonaAPI.class);

    PersonaBl personaBl;

    @Autowired
    public PersonaAPI(PersonaBl personaBl) {
        this.personaBl = personaBl;
    }

    // Endpoint para obtener todas las personas
    @GetMapping
    public ResponseDTO getAllPersonas() {
        List<Persona> personaList;
        try{
            personaList = personaBl.getAllPersona();
            LOGGER.info("Personas encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las personas");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(personaList);
    }

    // Endpoint para obtener una persona por su id
    @GetMapping("/{id}")
    public ResponseDTO getPersonaById(@PathVariable Long id) {
        Persona persona;
        try{
            persona = personaBl.getPersonaById(id);
            LOGGER.info("Persona encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la persona");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(persona);
    }

    // Endpoint para crear una persona
    @PostMapping("/create")
    public ResponseDTO createPersona(@RequestBody Persona persona) {
        Persona personaCreada;
        try{

            personaCreada = personaBl.createPersona(persona);
            LOGGER.info("Persona creada");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la persona");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(personaCreada);
    }

    // Endpoint para conseguir el id de la ultima persona
    @GetMapping("/lastId")
    public ResponseDTO getLastInsertedPersonaId() {
        Long id;
        try{
            id = personaBl.getLastIdPersona();
            LOGGER.info("Id de la ultima persona encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el id de la ultima persona");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(id);
    }

    // Endpoint para modificar una persona por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        Persona personaActualizada;
        try{
            personaActualizada = personaBl.updatePersona(id, persona);
            LOGGER.info("Persona actualizada");
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la persona");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(personaActualizada);
    }

    // Endpoint para eliminar una persona
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deletePersona(@PathVariable Long id) {
        try{
            personaBl.deletePersona(id);
            LOGGER.info("Persona eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la persona");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO("Persona eliminada");
    }


}

