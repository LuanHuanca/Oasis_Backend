package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolPermisoDao extends JpaRepository<RolPermiso, Long> {
    List<RolPermiso> findByRol_Rol(String rol);
}