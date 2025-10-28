package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
    
    /**
     * Obtiene todos los permisos de un rol específico
     */
    List<RolPermiso> findByRolIdRol(Long rolId);
    
    /**
     * Verifica si un rol ya tiene un permiso específico
     */
    boolean existsByRolIdRolAndPermisoIdPermiso(Long rolId, Long permisoId);
    
    /**
     * Elimina un permiso específico de un rol
     */
    void deleteByRolIdRolAndPermisoIdPermiso(Long rolId, Long permisoId);
    
    /**
     * Obtiene permisos de un rol con información completa
     */
    @Query("SELECT rp FROM RolPermiso rp JOIN FETCH rp.permiso WHERE rp.rol.idRol = :rolId")
    List<RolPermiso> findPermisosByRolId(@Param("rolId") Long rolId);
}
