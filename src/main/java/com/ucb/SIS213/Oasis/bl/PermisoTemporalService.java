package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.PermisoTemporalRepository;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.Permiso;
import com.ucb.SIS213.Oasis.entity.PermisoTemporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PermisoTemporalService {

    private final PermisoTemporalRepository permisoTemporalRepository;

    @Autowired
    public PermisoTemporalService(PermisoTemporalRepository permisoTemporalRepository) {
        this.permisoTemporalRepository = permisoTemporalRepository;
    }

    /**
     * Asigna un permiso temporal a un administrador
     */
    @Transactional
    public PermisoTemporal asignarPermisoTemporal(Long adminId, Long permisoId, LocalDateTime fechaFin, String motivo) {
        // Verificar que la fecha de fin sea futura
        if (fechaFin.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de fin debe ser futura");
        }

        // Verificar si ya tiene este permiso temporal activo
        if (permisoTemporalRepository.existsPermisoTemporalActivo(adminId, permisoId, LocalDateTime.now())) {
            throw new RuntimeException("El administrador ya tiene este permiso temporal activo");
        }

        PermisoTemporal permisoTemporal = new PermisoTemporal();
        
        Admin admin = new Admin();
        admin.setIdAdmin(adminId);
        permisoTemporal.setAdmin(admin);
        
        Permiso permiso = new Permiso();
        permiso.setIdPermiso(permisoId);
        permisoTemporal.setPermiso(permiso);
        
        permisoTemporal.setFechaInicio(LocalDateTime.now());
        permisoTemporal.setFechaFin(fechaFin);
        permisoTemporal.setMotivo(motivo);
        permisoTemporal.setActivo(true);
        permisoTemporal.setFechaCreacion(LocalDateTime.now());

        return permisoTemporalRepository.save(permisoTemporal);
    }

    /**
     * Asigna un permiso temporal con duración en días
     */
    @Transactional
    public PermisoTemporal asignarPermisoTemporalPorDias(Long adminId, Long permisoId, int dias, String motivo) {
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(dias);
        return asignarPermisoTemporal(adminId, permisoId, fechaFin, motivo);
    }

    /**
     * Asigna un permiso temporal con duración en horas
     */
    @Transactional
    public PermisoTemporal asignarPermisoTemporalPorHoras(Long adminId, Long permisoId, int horas, String motivo) {
        LocalDateTime fechaFin = LocalDateTime.now().plusHours(horas);
        return asignarPermisoTemporal(adminId, permisoId, fechaFin, motivo);
    }

    /**
     * Obtiene todos los permisos temporales de un administrador
     */
    public List<PermisoTemporal> getPermisosTemporalesByAdmin(Long adminId) {
        return permisoTemporalRepository.findByAdminIdAdmin(adminId);
    }

    /**
     * Obtiene permisos temporales activos de un administrador
     */
    public List<PermisoTemporal> getPermisosTemporalesActivosByAdmin(Long adminId) {
        return permisoTemporalRepository.findByAdminIdAdminAndActivoTrue(adminId);
    }

    /**
     * Obtiene permisos temporales válidos (activos y no expirados) de un administrador
     */
    public List<PermisoTemporal> getPermisosTemporalesValidosByAdmin(Long adminId) {
        return permisoTemporalRepository.findPermisosTemporalesValidos(adminId, LocalDateTime.now());
    }

    /**
     * Extiende un permiso temporal existente
     */
    @Transactional
    public PermisoTemporal extenderPermisoTemporal(Long permisoTemporalId, LocalDateTime nuevaFechaFin) {
        PermisoTemporal permisoTemporal = permisoTemporalRepository.findById(permisoTemporalId)
                .orElseThrow(() -> new RuntimeException("Permiso temporal no encontrado"));

        if (!permisoTemporal.getActivo()) {
            throw new RuntimeException("No se puede extender un permiso temporal inactivo");
        }

        if (nuevaFechaFin.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La nueva fecha de fin debe ser futura");
        }

        permisoTemporal.setFechaFin(nuevaFechaFin);
        return permisoTemporalRepository.save(permisoTemporal);
    }

    /**
     * Revoca un permiso temporal (lo desactiva)
     */
    @Transactional
    public void revocarPermisoTemporal(Long permisoTemporalId) {
        PermisoTemporal permisoTemporal = permisoTemporalRepository.findById(permisoTemporalId)
                .orElseThrow(() -> new RuntimeException("Permiso temporal no encontrado"));

        permisoTemporal.setActivo(false);
        permisoTemporalRepository.save(permisoTemporal);
    }

    /**
     * Obtiene permisos temporales expirados
     */
    public List<PermisoTemporal> getPermisosExpirados() {
        return permisoTemporalRepository.findPermisosExpirados(LocalDateTime.now());
    }

    /**
     * Obtiene permisos temporales que expiran pronto (en las próximas 24 horas)
     */
    public List<PermisoTemporal> getPermisosPorExpirar() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime mañana = ahora.plusDays(1);
        return permisoTemporalRepository.findPermisosPorExpirar(ahora, mañana);
    }

    /**
     * Desactiva automáticamente permisos temporales expirados
     * Se ejecuta cada hora
     */
    @Scheduled(fixedRate = 3600000) // Cada hora
    @Transactional
    public void desactivarPermisosExpirados() {
        List<PermisoTemporal> permisosExpirados = getPermisosExpirados();
        for (PermisoTemporal permiso : permisosExpirados) {
            permiso.setActivo(false);
            permisoTemporalRepository.save(permiso);
        }
        
        if (!permisosExpirados.isEmpty()) {
            System.out.println("Se desactivaron " + permisosExpirados.size() + " permisos temporales expirados");
        }
    }

    /**
     * Verifica si un administrador tiene un permiso temporal válido
     */
    public boolean tienePermisoTemporalValido(Long adminId, Long permisoId) {
        return permisoTemporalRepository.existsPermisoTemporalActivo(adminId, permisoId, LocalDateTime.now());
    }

    /**
     * Obtiene estadísticas de permisos temporales
     */
    public String getEstadisticasPermisosTemporales() {
        List<PermisoTemporal> todos = permisoTemporalRepository.findAll();
        List<PermisoTemporal> activos = todos.stream().filter(PermisoTemporal::getActivo).toList();
        List<PermisoTemporal> expirados = getPermisosExpirados();
        List<PermisoTemporal> porExpirar = getPermisosPorExpirar();

        return String.format(
            "Total: %d, Activos: %d, Expirados: %d, Por expirar: %d",
            todos.size(), activos.size(), expirados.size(), porExpirar.size()
        );
    }

    /**
     * Desactiva todos los permisos temporales de un administrador
     */
    @Transactional
    public void desactivarTodosPermisosTemporales(Long adminId) {
        permisoTemporalRepository.desactivarTodosPermisosTemporales(adminId);
    }

    /**
     * Elimina permanentemente todos los permisos temporales de un administrador
     */
    @Transactional
    public void eliminarTodosPermisosTemporales(Long adminId) {
        permisoTemporalRepository.eliminarTodosPermisosTemporales(adminId);
    }
}
