package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VueloDao extends JpaRepository<Vuelo, Long>{

    @Query(value = "SELECT * FROM vuelo WHERE idViaje = ?1",
            nativeQuery = true)
    List<Vuelo> findVueloById(Long userId);

    public Vuelo findByOrigen(Long origen);
    public Vuelo findByDestino(Long destino);
    public Vuelo findByFechaInicio(Date fechaInicio);
    public Vuelo findByFechaFin(Date fechaFin);
    public Vuelo findByPrecio(Double precio);
    public Vuelo findByPersonas(Long personas);
    public Vuelo findByNroReserva(String nroReserva);
}
