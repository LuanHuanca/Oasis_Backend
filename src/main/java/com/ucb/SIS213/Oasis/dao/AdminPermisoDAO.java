package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPermisoDAO extends JpaRepository<AdminPermiso, Long> {

    @Query("SELECT ap FROM AdminPermiso ap WHERE ap.admin.idAdmin = :adminId")
    List<AdminPermiso> findPermisosByAdminId(Long adminId);
}