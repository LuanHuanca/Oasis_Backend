package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.AdminPermisoDAO;
import com.ucb.SIS213.Oasis.dao.AdminDao;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;
import com.ucb.SIS213.Oasis.entity.PermisoTemporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionPermisosService {

    private final AdminPermisoDAO adminPermisoDAO;
    private final AdminDao adminDao;
    private final RolPermisoService rolPermisoService;
    private final PermisoTemporalService permisoTemporalService;

    @Autowired
    public GestionPermisosService(AdminPermisoDAO adminPermisoDAO, 
                                 AdminDao adminDao,
                                 RolPermisoService rolPermisoService,
                                 PermisoTemporalService permisoTemporalService) {
        this.adminPermisoDAO = adminPermisoDAO;
        this.adminDao = adminDao;
        this.rolPermisoService = rolPermisoService;
        this.permisoTemporalService = permisoTemporalService;
    }

    /**
     * Obtiene TODOS los permisos efectivos de un administrador
     * Incluye: permisos del rol + permisos adicionales + permisos temporales válidos
     */
    public List<Permiso> getAllPermisosEfectivos(Long adminId) {
        List<Permiso> permisosEfectivos = new ArrayList<>();

        // 1. Obtener admin real de la BD
        Admin admin = adminDao.findById(adminId).orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        
        // 2. Permisos del rol
        if (admin.getRol() != null) {
            List<Permiso> permisosRol = rolPermisoService.getPermisosSoloByRol(admin.getRol().getIdRol().longValue());
            permisosEfectivos.addAll(permisosRol);
        }

        // 3. Permisos adicionales activos
        List<AdminPermiso> permisosAdicionales = adminPermisoDAO.findPermisosAdicionalesActivos(adminId);
        List<Permiso> permisosAdicionalesSolo = permisosAdicionales.stream()
                .map(AdminPermiso::getPermiso)
                .collect(Collectors.toList());
        permisosEfectivos.addAll(permisosAdicionalesSolo);

        // 4. Permisos temporales válidos
        List<PermisoTemporal> permisosTemporales = permisoTemporalService.getPermisosTemporalesValidosByAdmin(adminId);
        List<Permiso> permisosTemporalesSolo = permisosTemporales.stream()
                .map(PermisoTemporal::getPermiso)
                .collect(Collectors.toList());
        permisosEfectivos.addAll(permisosTemporalesSolo);

        // Eliminar duplicados
        return permisosEfectivos.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un administrador tiene un permiso específico
     */
    public boolean tienePermiso(Long adminId, Long permisoId) {
        // Verificar permisos del rol
        Admin admin = adminDao.findById(adminId).orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        if (admin.getRol() != null && rolPermisoService.rolTienePermiso(admin.getRol().getIdRol().longValue(), permisoId)) {
            return true;
        }

        // Verificar permisos adicionales
        if (adminPermisoDAO.existsPermisoAdicionalActivo(adminId, permisoId)) {
            return true;
        }

        // Verificar permisos temporales
        return permisoTemporalService.tienePermisoTemporalValido(adminId, permisoId);
    }

    /**
     * Asigna un permiso adicional a un administrador
     */
    @Transactional
    public AdminPermiso asignarPermisoAdicional(Long adminId, Long permisoId) {
        // Verificar si ya tiene este permiso adicional
        if (adminPermisoDAO.existsPermisoAdicionalActivo(adminId, permisoId)) {
            throw new RuntimeException("El administrador ya tiene este permiso adicional");
        }

        AdminPermiso adminPermiso = new AdminPermiso();
        
        Admin admin = new Admin();
        admin.setIdAdmin(adminId);
        adminPermiso.setAdmin(admin);
        
        Permiso permiso = new Permiso();
        permiso.setIdPermiso(permisoId);
        adminPermiso.setPermiso(permiso);
        
        adminPermiso.setTipoPermiso("ADICIONAL");
        adminPermiso.setFechaAsignacion(LocalDateTime.now());
        adminPermiso.setActivo(true);

        return adminPermisoDAO.save(adminPermiso);
    }

    /**
     * Revoca un permiso adicional de un administrador
     */
    @Transactional
    public void revocarPermisoAdicional(Long adminId, Long permisoId) {
        adminPermisoDAO.desactivarPermiso(adminId, permisoId);
    }

    /**
     * Obtiene permisos adicionales de un administrador
     */
    public List<AdminPermiso> getPermisosAdicionales(Long adminId) {
        return adminPermisoDAO.findPermisosAdicionalesActivos(adminId);
    }

    /**
     * Obtiene permisos temporales de un administrador
     */
    public List<PermisoTemporal> getPermisosTemporales(Long adminId) {
        return permisoTemporalService.getPermisosTemporalesByAdmin(adminId);
    }

    /**
     * Obtiene permisos del rol de un administrador
     */
    public List<Permiso> getPermisosDelRol(Long adminId) {
        Admin admin = adminDao.findById(adminId).orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        if (admin.getRol() != null) {
            return rolPermisoService.getPermisosSoloByRol(admin.getRol().getIdRol().longValue());
        }
        return new ArrayList<>();
    }

    /**
     * Obtiene un resumen completo de permisos de un administrador
     */
    public PermisosResumenDTO getResumenPermisos(Long adminId) {
        PermisosResumenDTO resumen = new PermisosResumenDTO();
        resumen.setAdminId(adminId);
        resumen.setPermisosRol(getPermisosDelRol(adminId));
        resumen.setPermisosAdicionales(getPermisosAdicionales(adminId).stream()
                .map(AdminPermiso::getPermiso)
                .collect(Collectors.toList()));
        resumen.setPermisosTemporales(getPermisosTemporales(adminId).stream()
                .map(PermisoTemporal::getPermiso)
                .collect(Collectors.toList()));
        resumen.setPermisosEfectivos(getAllPermisosEfectivos(adminId));
        
        return resumen;
    }

    /**
     * DTO para el resumen de permisos
     */
    public static class PermisosResumenDTO {
        private Long adminId;
        private List<Permiso> permisosRol;
        private List<Permiso> permisosAdicionales;
        private List<Permiso> permisosTemporales;
        private List<Permiso> permisosEfectivos;

        // Getters y setters
        public Long getAdminId() { return adminId; }
        public void setAdminId(Long adminId) { this.adminId = adminId; }
        
        public List<Permiso> getPermisosRol() { return permisosRol; }
        public void setPermisosRol(List<Permiso> permisosRol) { this.permisosRol = permisosRol; }
        
        public List<Permiso> getPermisosAdicionales() { return permisosAdicionales; }
        public void setPermisosAdicionales(List<Permiso> permisosAdicionales) { this.permisosAdicionales = permisosAdicionales; }
        
        public List<Permiso> getPermisosTemporales() { return permisosTemporales; }
        public void setPermisosTemporales(List<Permiso> permisosTemporales) { this.permisosTemporales = permisosTemporales; }
        
        public List<Permiso> getPermisosEfectivos() { return permisosEfectivos; }
        public void setPermisosEfectivos(List<Permiso> permisosEfectivos) { this.permisosEfectivos = permisosEfectivos; }
    }

    /**
     * Limpia todos los permisos adicionales y temporales de un administrador.
     * Útil al cambiar de rol para que solo mantenga los permisos del nuevo rol.
     * @param idAdmin ID del administrador.
     */
    @Transactional
    public void limpiarTodosPermisosPersonales(Long idAdmin) {
        // Desactivar todos los permisos adicionales
        adminPermisoDAO.desactivarTodosPermisosAdicionales(idAdmin);
        
        // Desactivar todos los permisos temporales
        permisoTemporalService.desactivarTodosPermisosTemporales(idAdmin);
    }

    /**
     * Elimina permanentemente todos los permisos adicionales y temporales de un administrador.
     * ⚠️ CUIDADO: Esta acción es irreversible.
     * @param idAdmin ID del administrador.
     */
    @Transactional
    public void eliminarTodosPermisosPersonales(Long idAdmin) {
        // Eliminar físicamente todos los permisos adicionales
        adminPermisoDAO.eliminarTodosPermisosAdicionales(idAdmin);
        
        // Eliminar físicamente todos los permisos temporales
        permisoTemporalService.eliminarTodosPermisosTemporales(idAdmin);
    }

    /**
     * Limpia solo los permisos adicionales de un administrador.
     * @param idAdmin ID del administrador.
     */
    @Transactional
    public void limpiarPermisosAdicionales(Long idAdmin) {
        adminPermisoDAO.desactivarTodosPermisosAdicionales(idAdmin);
    }

    /**
     * Limpia solo los permisos temporales de un administrador.
     * @param idAdmin ID del administrador.
     */
    @Transactional
    public void limpiarPermisosTemporales(Long idAdmin) {
        permisoTemporalService.desactivarTodosPermisosTemporales(idAdmin);
    }
}
