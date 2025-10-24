package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;  
import java.util.List;

@Repository   // para que Spring lo reconozca como un DAO
public interface RolPermisoDao extends JpaRepository<RolPermiso, Integer> {

    // Método para obtener todos los permisos asociados a un rol específico
    List<RolPermiso> findByRol_IdRol(Integer idRol);
}


