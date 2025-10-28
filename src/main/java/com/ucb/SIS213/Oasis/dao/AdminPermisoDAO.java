package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminPermisoDAO extends JpaRepository<AdminPermiso, Long> {

    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId")
    List<AdminPermiso> findPermisosByAdminId(Long adminId);

    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.permiso.idPermiso = :permisoId")
    AdminPermiso findByAdminIdAndPermisoId(Long adminId, Long permisoId);

    @Query("SELECT ap.permiso FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId")
    List<Permiso> findOnlyPermisosByAdminId(Long adminId);

    /**
     * Obtiene permisos adicionales activos de un admin
     */
    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.tipoPermiso = 'ADICIONAL' AND ap.activo = true")
    List<AdminPermiso> findPermisosAdicionalesActivos(@Param("adminId") Long adminId);

    /**
     * Obtiene todos los permisos activos de un admin (incluyendo tipo)
     */
    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.activo = true")
    List<AdminPermiso> findPermisosActivosByAdminId(@Param("adminId") Long adminId);

    /**
     * Verifica si un admin ya tiene un permiso adicional específico
     */
    @Query("SELECT COUNT(ap) > 0 FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.permiso.idPermiso = :permisoId AND ap.tipoPermiso = 'ADICIONAL' AND ap.activo = true")
    boolean existsPermisoAdicionalActivo(@Param("adminId") Long adminId, @Param("permisoId") Long permisoId);

    /**
     * Obtiene permisos por tipo específico
     */
    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.tipoPermiso = :tipoPermiso AND ap.activo = true")
    List<AdminPermiso> findPermisosByTipo(@Param("adminId") Long adminId, @Param("tipoPermiso") String tipoPermiso);

    /**
     * Desactiva un permiso específico de un admin
     */
    @Modifying
    @Transactional
    @Query("UPDATE AdminPermiso ap SET ap.activo = false WHERE ap.admin.idAdmin = :adminId AND ap.permiso.idPermiso = :permisoId")
    void desactivarPermiso(@Param("adminId") Long adminId, @Param("permisoId") Long permisoId);

    /**
     * Desactiva todos los permisos adicionales de un admin
     */
    @Modifying
    @Transactional
    @Query("UPDATE AdminPermiso ap SET ap.activo = false WHERE ap.admin.idAdmin = :adminId AND ap.tipoPermiso = 'ADICIONAL'")
    void desactivarTodosPermisosAdicionales(@Param("adminId") Long adminId);

    /**
     * Elimina físicamente todos los permisos adicionales de un admin
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.tipoPermiso = 'ADICIONAL'")
    void eliminarTodosPermisosAdicionales(@Param("adminId") Long adminId);

}