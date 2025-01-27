package com.ucb.SIS213.Oasis.dao;

import com.ucb.SIS213.Oasis.entity.FormaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagoDao extends JpaRepository<FormaPago, Long>{

    @Query(value = "SELECT * FROM FormaPago WHERE idFormP = ?1",
            nativeQuery = true)
    List<FormaPago> findFormaPagoById(Long userId);

    public FormaPago findByFormapago(String nombre);

}
