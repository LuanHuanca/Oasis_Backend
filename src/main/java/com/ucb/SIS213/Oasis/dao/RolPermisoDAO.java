package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.RolPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RolPermisoDAO extends JpaRepository<RolPermiso, Long> {
    @Query("SELECT rp FROM RolPermiso rp WHERE rp.rol.idRol = :rolId")
    List<RolPermiso> findPermisosByRolId(Long rolId);

    @Query("SELECT rp.permiso FROM RolPermiso rp WHERE rp.rol.idRol = :rolId")
    List<Permiso> findOnlyPermisosByRolId(Long rolId);

    void deleteByRolIdAndPermisoId(Long rolId, Long permisoId);
}