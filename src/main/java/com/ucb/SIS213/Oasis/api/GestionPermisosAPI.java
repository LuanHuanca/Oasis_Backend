package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.GestionPermisosService;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;
import com.ucb.SIS213.Oasis.entity.PermisoTemporal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gestion-permisos")
@CrossOrigin(origins = "*")
public class GestionPermisosAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionPermisosAPI.class);

    @Autowired
    private GestionPermisosService gestionPermisosService;

    /**
     * Obtiene TODOS los permisos efectivos de un administrador
     */
    @GetMapping("/admin/{adminId}/permisos-efectivos")
    public ResponseDTO getAllPermisosEfectivos(@PathVariable Long adminId) {
        try {
            List<Permiso> permisosEfectivos = gestionPermisosService.getAllPermisosEfectivos(adminId);
            LOGGER.info("Permisos efectivos del admin obtenidos exitosamente");
            return new ResponseDTO(permisosEfectivos);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos efectivos del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Verifica si un administrador tiene un permiso específico
     */
    @GetMapping("/admin/{adminId}/tiene-permiso/{permisoId}")
    public ResponseDTO tienePermiso(@PathVariable Long adminId, @PathVariable Long permisoId) {
        try {
            boolean tienePermiso = gestionPermisosService.tienePermiso(adminId, permisoId);
            LOGGER.info("Verificación de permiso del admin realizada");
            return new ResponseDTO(tienePermiso);
        } catch (RuntimeException e) {
            LOGGER.error("Error al verificar permiso del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Asigna un permiso adicional a un administrador
     */
    @PostMapping("/admin/{adminId}/permiso-adicional")
    public ResponseDTO asignarPermisoAdicional(@PathVariable Long adminId, @RequestBody Map<String, Object> requestBody) {
        try {
            Long permisoId = Long.valueOf(requestBody.get("permisoId").toString());
            
            AdminPermiso adminPermiso = gestionPermisosService.asignarPermisoAdicional(adminId, permisoId);
            LOGGER.info("Permiso adicional asignado al admin exitosamente");
            return new ResponseDTO(adminPermiso);
        } catch (RuntimeException e) {
            LOGGER.error("Error al asignar permiso adicional al admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Revoca un permiso adicional de un administrador
     */
    @DeleteMapping("/admin/{adminId}/permiso-adicional/{permisoId}")
    public ResponseDTO revocarPermisoAdicional(@PathVariable Long adminId, @PathVariable Long permisoId) {
        try {
            gestionPermisosService.revocarPermisoAdicional(adminId, permisoId);
            LOGGER.info("Permiso adicional revocado del admin exitosamente");
            return new ResponseDTO("Permiso adicional revocado exitosamente");
        } catch (RuntimeException e) {
            LOGGER.error("Error al revocar permiso adicional del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos adicionales de un administrador
     */
    @GetMapping("/admin/{adminId}/permisos-adicionales")
    public ResponseDTO getPermisosAdicionales(@PathVariable Long adminId) {
        try {
            List<AdminPermiso> permisosAdicionales = gestionPermisosService.getPermisosAdicionales(adminId);
            LOGGER.info("Permisos adicionales del admin obtenidos exitosamente");
            return new ResponseDTO(permisosAdicionales);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos adicionales del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos temporales de un administrador
     */
    @GetMapping("/admin/{adminId}/permisos-temporales")
    public ResponseDTO getPermisosTemporales(@PathVariable Long adminId) {
        try {
            List<PermisoTemporal> permisosTemporales = gestionPermisosService.getPermisosTemporales(adminId);
            LOGGER.info("Permisos temporales del admin obtenidos exitosamente");
            return new ResponseDTO(permisosTemporales);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos temporales del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene permisos del rol de un administrador
     */
    @GetMapping("/admin/{adminId}/permisos-rol")
    public ResponseDTO getPermisosDelRol(@PathVariable Long adminId) {
        try {
            List<Permiso> permisosRol = gestionPermisosService.getPermisosDelRol(adminId);
            LOGGER.info("Permisos del rol del admin obtenidos exitosamente");
            return new ResponseDTO(permisosRol);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener permisos del rol del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Obtiene un resumen completo de permisos de un administrador
     */
    @GetMapping("/admin/{adminId}/resumen")
    public ResponseDTO getResumenPermisos(@PathVariable Long adminId) {
        try {
            GestionPermisosService.PermisosResumenDTO resumen = gestionPermisosService.getResumenPermisos(adminId);
            LOGGER.info("Resumen de permisos del admin obtenido exitosamente");
            return new ResponseDTO(resumen);
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener resumen de permisos del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Verifica múltiples permisos de un administrador
     */
    @PostMapping("/admin/{adminId}/verificar-permisos")
    public ResponseDTO verificarMultiplesPermisos(@PathVariable Long adminId, @RequestBody Map<String, Object> requestBody) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> permisoIds = (List<Long>) requestBody.get("permisoIds");
            
            Map<Long, Boolean> resultados = permisoIds.stream()
                    .collect(java.util.stream.Collectors.toMap(
                            permisoId -> permisoId,
                            permisoId -> gestionPermisosService.tienePermiso(adminId, permisoId)
                    ));
            
            LOGGER.info("Verificación múltiple de permisos del admin realizada");
            return new ResponseDTO(resultados);
        } catch (RuntimeException e) {
            LOGGER.error("Error al verificar múltiples permisos del admin", e);
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Limpia todos los permisos adicionales y temporales de un administrador.
     * Útil al cambiar de rol para que solo mantenga los permisos del nuevo rol.
     */
    @DeleteMapping("/admin/{adminId}/limpiar-todos-permisos")
    public ResponseDTO limpiarTodosPermisosPersonales(@PathVariable Long adminId) {
        try {
            gestionPermisosService.limpiarTodosPermisosPersonales(adminId);
            LOGGER.info("Todos los permisos personales del admin {} han sido limpiados.", adminId);
            return new ResponseDTO("Todos los permisos adicionales y temporales han sido limpiados. El admin solo mantiene los permisos de su rol.");
        } catch (RuntimeException e) {
            LOGGER.error("Error al limpiar todos los permisos del admin {}: {}", adminId, e.getMessage());
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Elimina permanentemente todos los permisos adicionales y temporales de un administrador.
     * ⚠️ CUIDADO: Esta acción es irreversible.
     */
    @DeleteMapping("/admin/{adminId}/eliminar-todos-permisos")
    public ResponseDTO eliminarTodosPermisosPersonales(@PathVariable Long adminId) {
        try {
            gestionPermisosService.eliminarTodosPermisosPersonales(adminId);
            LOGGER.info("Todos los permisos personales del admin {} han sido eliminados permanentemente.", adminId);
            return new ResponseDTO("Todos los permisos adicionales y temporales han sido eliminados permanentemente.");
        } catch (RuntimeException e) {
            LOGGER.error("Error al eliminar todos los permisos del admin {}: {}", adminId, e.getMessage());
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Limpia solo los permisos adicionales de un administrador.
     */
    @DeleteMapping("/admin/{adminId}/limpiar-adicionales")
    public ResponseDTO limpiarPermisosAdicionales(@PathVariable Long adminId) {
        try {
            gestionPermisosService.limpiarPermisosAdicionales(adminId);
            LOGGER.info("Permisos adicionales del admin {} han sido limpiados.", adminId);
            return new ResponseDTO("Permisos adicionales han sido desactivados.");
        } catch (RuntimeException e) {
            LOGGER.error("Error al limpiar permisos adicionales del admin {}: {}", adminId, e.getMessage());
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    /**
     * Limpia solo los permisos temporales de un administrador.
     */
    @DeleteMapping("/admin/{adminId}/limpiar-temporales")
    public ResponseDTO limpiarPermisosTemporales(@PathVariable Long adminId) {
        try {
            gestionPermisosService.limpiarPermisosTemporales(adminId);
            LOGGER.info("Permisos temporales del admin {} han sido limpiados.", adminId);
            return new ResponseDTO("Permisos temporales han sido desactivados.");
        } catch (RuntimeException e) {
            LOGGER.error("Error al limpiar permisos temporales del admin {}: {}", adminId, e.getMessage());
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }
}
