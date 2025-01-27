package com.ucb.SIS213.Oasis.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AuditoriaBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Auditoria;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/auditoria")
public class AuditoriaAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AuditoriaAPI.class);

    AuditoriaBl auditoriaBl;

    @Autowired
    public AuditoriaAPI(AuditoriaBl auditoriaBl) {
        this.auditoriaBl = auditoriaBl;
    }

    // Endpoint para obtener todos los auditorias
    @GetMapping
    public ResponseDTO getAllAuditorias() {
        List<Auditoria> auditoriaList;
        try{
            auditoriaList = auditoriaBl.getAllAuditoria();
            LOGGER.info("Auditorias encontrados");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener los auditorias");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(auditoriaList);
    }

    // Endpoint para obtener un auditoria por su id
    @GetMapping("/{id}")
    public ResponseDTO getAuditoriaById(@PathVariable Long id) {
        Auditoria auditoria;
        try{
            auditoria = auditoriaBl.getAuditoriaById(id);
            LOGGER.info("Auditoria encontrado");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener el auditoria");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(auditoria);
    }

    // Endpoint para crear un auditoria
    @PostMapping("/create")
    public ResponseDTO createAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria auditoriaCreado;
        try{
            
            auditoriaCreado = auditoriaBl.createAuditoria(auditoria);
            LOGGER.info("Auditoria creado");
        } catch (RuntimeException e){
            LOGGER.error("Error al crear el auditoria");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(auditoriaCreado);
    }

    // Endpoint para actualizar un auditoria
    @PutMapping("/update")
    public ResponseDTO updateAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria auditoriaActualizado;
        try {
            LOGGER.info("Auditoria: " + auditoria.getIdAudit().toString());
            auditoriaActualizado = auditoriaBl.updateAuditoria(auditoria);
            LOGGER.info("Auditoria actualizado");
        } catch (RuntimeException e) {
            LOGGER.error("Error al actualizar el auditoria");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(auditoriaActualizado);
    }
}
