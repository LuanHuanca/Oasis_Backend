package com.ucb.SIS213.Oasis.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.AuditoriaBl;
import com.ucb.SIS213.Oasis.dto.AuditoriaDTO;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.Auditoria;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.Cliente;
import com.ucb.SIS213.Oasis.dao.AdminDao;
import com.ucb.SIS213.Oasis.dao.ClienteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auditoria")
public class AuditoriaAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditoriaAPI.class);

    private AuditoriaBl auditoriaBl;
    private AdminDao adminDao;
    private ClienteDao clienteDao;

    @Autowired
    public AuditoriaAPI(AuditoriaBl auditoriaBl, AdminDao adminDao, ClienteDao clienteDao) {
        this.auditoriaBl = auditoriaBl;
        this.adminDao = adminDao;
        this.clienteDao = clienteDao;
    }

    // Endpoint para obtener todas las auditorias
    @GetMapping
    public ResponseDTO getAllAuditorias() {
        List<Auditoria> auditorias = auditoriaBl.getAllAuditoria();
        List<AuditoriaDTO> auditoriaDTOs = auditorias.stream().map(auditoria -> {
            String correo = null;
            if (auditoria.getAdminId() != null) {
                Admin admin = adminDao.findById(auditoria.getAdminId().longValue()).orElse(null);
                if (admin != null) {
                    correo = admin.getCorreo();
                }
            } else if (auditoria.getClienteId() != null) {
                Cliente cliente = clienteDao.findById(auditoria.getClienteId().longValue()).orElse(null);
                if (cliente != null) {
                    correo = cliente.getCorreo();
                }
            }
            return new AuditoriaDTO(auditoria, correo);
        }).collect(Collectors.toList());
        return new ResponseDTO(auditoriaDTOs);
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

    // Endpoint para crear una nueva auditoria
    @PostMapping("/create")
    public ResponseDTO createAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria auditoriaCreado;
        try {
            String correo = auditoria.getCorreo();
            Admin admin = adminDao.findByCorreo(correo);
            Cliente cliente = clienteDao.findByCorreo(correo);

            if (admin != null) {
                auditoria.setAdminId(admin.getIdAdmin().intValue());
                auditoria.setIp(auditoria.getIp());
            } else if (cliente != null) {
                auditoria.setClienteId(cliente.getIdCliente().intValue());
                auditoria.setIp(auditoria.getIp());
            } else {
                return new ResponseDTO("TASK-1001", "Usuario no encontrado");
            }

            auditoriaCreado = auditoriaBl.createAuditoria(auditoria);
            LOGGER.info("Auditoria creada");
        } catch (RuntimeException e) {
            LOGGER.error("Error al crear la auditoria", e);
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
