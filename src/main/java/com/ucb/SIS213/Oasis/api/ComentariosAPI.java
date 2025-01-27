package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.ComentariosBl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.ComentariosBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Comentarios;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/comentarios")
public class ComentariosAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ComentariosAPI.class);

    ComentariosBl comentariosBl;

    @Autowired
    public ComentariosAPI(ComentariosBl comentariosBl) {
        this.comentariosBl = comentariosBl;
    }

    // Endpoint para obtener todos los comentarios
    @GetMapping
    public ResponseDTO getAllComentarios() {
        List<Comentarios> comentariosList;
        try{
            comentariosList = comentariosBl.getAllComentarios();
            LOGGER.info("Comentarios encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los comentarios");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(comentariosList);
    }

    // Endpoint para obtener un comentario por su id
    @GetMapping("/{id}")
    public ResponseDTO getComentariosById(@PathVariable Long id) {
        Comentarios comentarios;
        try{
            comentarios = comentariosBl.getComentariosById(id);
            LOGGER.info("Comentario encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el comentario");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(comentarios);
    }

    // Endpoint para crear un comentario
    @PostMapping("/create")
    public ResponseDTO createComentarios(@RequestBody Comentarios comentarios) {
        Comentarios comentariosCreado;
        try{
            comentariosCreado = comentariosBl.createComentarios(comentarios);
            LOGGER.info("Comentario creado");
            return new ResponseDTO(comentariosCreado);
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el comentario");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para modificar un comentario por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateComentarios(@PathVariable Long id, @RequestBody Comentarios comentarios) {
        Comentarios comentariosActualizado;
        try{
            comentariosActualizado = comentariosBl.updateComentariosById(id, comentarios);
            LOGGER.info("Comentario actualizado");
            return new ResponseDTO(comentariosActualizado);
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar el comentario");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para eliminar un comentario
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteComentarios(@PathVariable Long id) {
        try{
            comentariosBl.deleteComentariosById(id);
            LOGGER.info("Comentario eliminado");
            return new ResponseDTO("Comentario eliminado");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar el comentario");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

}
