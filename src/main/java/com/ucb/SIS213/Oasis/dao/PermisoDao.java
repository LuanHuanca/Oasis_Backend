package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoDao extends JpaRepository<Permiso, Long> {
}