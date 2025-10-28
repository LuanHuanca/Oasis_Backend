package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.PermisoTemporal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PermisoTemporalRepository extends JpaRepository<PermisoTemporal, Long> {
    
    /**
     * Obtiene todos los permisos temporales de un admin
     */
    List<PermisoTemporal> findByAdminIdAdmin(Long adminId);
    
    /**
     * Obtiene permisos temporales activos de un admin
     */
    List<PermisoTemporal> findByAdminIdAdminAndActivoTrue(Long adminId);
    
    /**
     * Obtiene permisos temporales válidos (activos y no expirados) de un admin
     */
    @Query("SELECT pt FROM PermisoTemporal pt WHERE pt.admin.idAdmin = :adminId AND pt.activo = true AND pt.fechaFin > :now")
    List<PermisoTemporal> findPermisosTemporalesValidos(@Param("adminId") Long adminId, @Param("now") LocalDateTime now);
    
    /**
     * Obtiene permisos temporales expirados
     */
    @Query("SELECT pt FROM PermisoTemporal pt WHERE pt.fechaFin < :now AND pt.activo = true")
    List<PermisoTemporal> findPermisosExpirados(@Param("now") LocalDateTime now);
    
    /**
     * Obtiene permisos temporales que expiran pronto (en las próximas 24 horas)
     */
    @Query("SELECT pt FROM PermisoTemporal pt WHERE pt.fechaFin BETWEEN :now AND :tomorrow AND pt.activo = true")
    List<PermisoTemporal> findPermisosPorExpirar(@Param("now") LocalDateTime now, @Param("tomorrow") LocalDateTime tomorrow);
    
    /**
     * Verifica si un admin ya tiene un permiso temporal específico activo
     */
    @Query("SELECT COUNT(pt) > 0 FROM PermisoTemporal pt WHERE pt.admin.idAdmin = :adminId AND pt.permiso.idPermiso = :permisoId AND pt.activo = true AND pt.fechaFin > :now")
    boolean existsPermisoTemporalActivo(@Param("adminId") Long adminId, @Param("permisoId") Long permisoId, @Param("now") LocalDateTime now);

    /**
     * Desactiva todos los permisos temporales de un admin
     */
    @Modifying
    @Transactional
    @Query("UPDATE PermisoTemporal pt SET pt.activo = false WHERE pt.admin.idAdmin = :adminId")
    void desactivarTodosPermisosTemporales(@Param("adminId") Long adminId);

    /**
     * Elimina físicamente todos los permisos temporales de un admin
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PermisoTemporal pt WHERE pt.admin.idAdmin = :adminId")
    void eliminarTodosPermisosTemporales(@Param("adminId") Long adminId);
}
