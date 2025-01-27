package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentariosDao extends JpaRepository<Comentarios, Long>{

    @Query(value = "SELECT * FROM Comentarios WHERE idComentario = ?1",
            nativeQuery = true)
    List<Comentarios> findComentariosById(Long userId);

    @Query(value = "SELECT MAX(idComentario) FROM Comentarios;", nativeQuery = true)
    Long getLastInsertedComentarioId();

}
