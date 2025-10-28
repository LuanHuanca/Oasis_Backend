package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.PermisoTemporalService;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.PermisoTemporal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/permiso-temporal")
@CrossOrigin(origins = "*")
public class PermisoTemporalAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermisoTemporalAPI.class);

    @Autowired
    private PermisoTemporalService permisoTemporalService;

    /**
     * Asigna un permiso temporal con fecha específica
     */
    @PostMapping("/asignar")
    public ResponseDTO asignarPermisoTemporal(@RequestBody Map<String, Object> requestBody) {
        try {
            Long adminId = Long.valueOf(requestBody.get("adminId").toString());
            Long permisoId = Long.valueOf(requestBody.get("permisoId").toString());
            String fechaFinStr = requestBody.get("fechaFin").toString();
            String motivo = requestBody.get("motivo").toString();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);

            PermisoTemporal permisoTemporal = permisoTemporalService.asignarPermisoTemporal(adminId, permisoId, fechaFin, motivo);
            LOGGER.info("Permiso temporal asignado exitosamente");
            return new ResponseDTO(permisoTemporal);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permiso temporal", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Asigna un permiso temporal por días
     */
    @PostMapping("/asignar-por-dias")
    public ResponseDTO asignarPermisoTemporalPorDias(@RequestBody Map<String, Object> requestBody) {
        try {
            Long adminId = Long.valueOf(requestBody.get("adminId").toString());
            Long permisoId = Long.valueOf(requestBody.get("permisoId").toString());
            Integer dias = Integer.valueOf(requestBody.get("dias").toString());
            String motivo = requestBody.get("motivo").toString();

            PermisoTemporal permisoTemporal = permisoTemporalService.asignarPermisoTemporalPorDias(adminId, permisoId, dias, motivo);
            LOGGER.info("Permiso temporal por días asignado exitosamente");
            return new ResponseDTO(permisoTemporal);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permiso temporal por días", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Asigna un permiso temporal por horas
     */
    @PostMapping("/asignar-por-horas")
    public ResponseDTO asignarPermisoTemporalPorHoras(@RequestBody Map<String, Object> requestBody) {
        try {
            Long adminId = Long.valueOf(requestBody.get("adminId").toString());
            Long permisoId = Long.valueOf(requestBody.get("permisoId").toString());
            Integer horas = Integer.valueOf(requestBody.get("horas").toString());
            String motivo = requestBody.get("motivo").toString();

            PermisoTemporal permisoTemporal = permisoTemporalService.asignarPermisoTemporalPorHoras(adminId, permisoId, horas, motivo);
            LOGGER.info("Permiso temporal por horas asignado exitosamente");
            return new ResponseDTO(permisoTemporal);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permiso temporal por horas", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene todos los permisos temporales de un administrador
     */
    @GetMapping("/admin/{adminId}")
    public ResponseDTO getPermisosTemporalesByAdmin(@PathVariable Long adminId) {
        try {
            List<PermisoTemporal> permisosTemporales = permisoTemporalService.getPermisosTemporalesByAdmin(adminId);
            LOGGER.info("Permisos temporales del admin obtenidos exitosamente");
            return new ResponseDTO(permisosTemporales);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos temporales activos de un administrador
     */
    @GetMapping("/admin/{adminId}/activos")
    public ResponseDTO getPermisosTemporalesActivosByAdmin(@PathVariable Long adminId) {
        try {
            List<PermisoTemporal> permisosTemporales = permisoTemporalService.getPermisosTemporalesActivosByAdmin(adminId);
            LOGGER.info("Permisos temporales activos del admin obtenidos exitosamente");
            return new ResponseDTO(permisosTemporales);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales activos del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos temporales válidos de un administrador
     */
    @GetMapping("/admin/{adminId}/validos")
    public ResponseDTO getPermisosTemporalesValidosByAdmin(@PathVariable Long adminId) {
        try {
            List<PermisoTemporal> permisosTemporales = permisoTemporalService.getPermisosTemporalesValidosByAdmin(adminId);
            LOGGER.info("Permisos temporales válidos del admin obtenidos exitosamente");
            return new ResponseDTO(permisosTemporales);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales válidos del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Extiende un permiso temporal
     */
    @PutMapping("/{permisoTemporalId}/extender")
    public ResponseDTO extenderPermisoTemporal(@PathVariable Long permisoTemporalId, @RequestBody Map<String, Object> requestBody) {
        try {
            String nuevaFechaFinStr = requestBody.get("nuevaFechaFin").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime nuevaFechaFin = LocalDateTime.parse(nuevaFechaFinStr, formatter);

            PermisoTemporal permisoTemporal = permisoTemporalService.extenderPermisoTemporal(permisoTemporalId, nuevaFechaFin);
            LOGGER.info("Permiso temporal extendido exitosamente");
            return new ResponseDTO(permisoTemporal);
        } catch (RuntimeException e) {
            LOGGER.error("Error al extender permiso temporal", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Revoca un permiso temporal
     */
    @PutMapping("/{permisoTemporalId}/revocar")
    public ResponseDTO revocarPermisoTemporal(@PathVariable Long permisoTemporalId) {
        try {
            permisoTemporalService.revocarPermisoTemporal(permisoTemporalId);
            LOGGER.info("Permiso temporal revocado exitosamente");
            return new ResponseDTO("Permiso temporal revocado exitosamente");
        } catch (RuntimeException e) {
            LOGGER.error("Error al revocar permiso temporal", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos temporales expirados
     */
    @GetMapping("/expirados")
    public ResponseDTO getPermisosExpirados() {
        try {
            List<PermisoTemporal> permisosExpirados = permisoTemporalService.getPermisosExpirados();
            LOGGER.info("Permisos temporales expirados obtenidos exitosamente");
            return new ResponseDTO(permisosExpirados);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales expirados", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos temporales que expiran pronto
     */
    @GetMapping("/por-expirar")
    public ResponseDTO getPermisosPorExpirar() {
        try {
            List<PermisoTemporal> permisosPorExpirar = permisoTemporalService.getPermisosPorExpirar();
            LOGGER.info("Permisos temporales por expirar obtenidos exitosamente");
            return new ResponseDTO(permisosPorExpirar);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales por expirar", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene estadísticas de permisos temporales
     */
    @GetMapping("/estadisticas")
    public ResponseDTO getEstadisticasPermisosTemporales() {
        try {
            String estadisticas = permisoTemporalService.getEstadisticasPermisosTemporales();
            LOGGER.info("Estadísticas de permisos temporales obtenidas exitosamente");
            return new ResponseDTO(estadisticas);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener estadísticas de permisos temporales", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }
}
