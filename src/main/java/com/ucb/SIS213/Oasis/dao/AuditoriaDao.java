package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuditoriaDao extends JpaRepository<Auditoria, Long> {

    @Query(value = "SELECT * FROM auditoria WHERE idAudit = ?1",
            nativeQuery = true)
    List<Auditoria> findAuditById(Long userId);

}
