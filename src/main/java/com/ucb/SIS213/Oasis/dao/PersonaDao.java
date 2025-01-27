package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaDao extends JpaRepository<Persona, Long> {

    @Query(value = "SELECT * FROM persona WHERE idPersona = ?1",
    nativeQuery = true)
    List<Persona> findLabelsByUserId(Long userId);

    @Query(value = "SELECT MAX(idPersona) FROM Persona;", nativeQuery = true)
    Long getLastInsertedPersonaId();


}
