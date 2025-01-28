package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolPermisoDao extends JpaRepository<RolPermiso, Long> {

    @Query("SELECT rp FROM RolPermiso rp JOIN rp.rol r JOIN Admin a ON r.idRol = a.rol.idRol WHERE a.idAdmin = :adminId")
    List<RolPermiso> findPermisosByAdminId(Long adminId);
}