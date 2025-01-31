package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.Permiso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPermisoDAO extends JpaRepository<AdminPermiso, Long> {

    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId")
    List<AdminPermiso> findPermisosByAdminId(Long adminId);

    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId AND ap.permiso.idPermiso = :permisoId")
    AdminPermiso findByAdminIdAndPermisoId(Long adminId, Long permisoId);

    @Query("SELECT ap.permiso FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId")
    List<Permiso> findOnlyPermisosByAdminId(Long adminId);

}