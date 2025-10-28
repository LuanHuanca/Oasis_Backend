package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.HistorialContrasenaService;
import com.ucb.SIS213.Oasis.entity.HistorialContrasena;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historial-contrasena")
@CrossOrigin(origins = "*")
public class HistorialContrasenaAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistorialContrasenaAPI.class);

    @Autowired
    private HistorialContrasenaService historialService;

    /**
     * Obtiene el historial de contraseñas de un cliente
     * @param idCliente ID del cliente
     * @return Lista del historial de contraseñas
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseDTO getHistorialCliente(@PathVariable Long idCliente) {
        try {
            List<HistorialContrasena> historial = historialService.findHistoryForCliente(idCliente);
            LOGGER.info("Historial de contraseñas del cliente obtenido");
            return new ResponseDTO(historial);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener el historial del cliente", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene el historial de contraseñas de un administrador
     * @param idAdmin ID del administrador
     * @return Lista del historial de contraseñas
     */
    @GetMapping("/admin/{idAdmin}")
    public ResponseDTO getHistorialAdmin(@PathVariable Long idAdmin) {
        try {
            List<HistorialContrasena> historial = historialService.findHistoryForAdmin(idAdmin);
            LOGGER.info("Historial de contraseñas del admin obtenido");
            return new ResponseDTO(historial);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener el historial del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }
}
