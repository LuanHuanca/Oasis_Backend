package com.ucb.SIS213.Oasis.entity;

public class RolService {
    
}
package com.ucb.SIS213.Oasis.service;

import com.ucb.SIS213.Oasis.entity.Rol;
import com.ucb.SIS213.Oasis.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Rol getRolById(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public void deleteRol(Integer id) {
        rolRepository.deleteById(id);
    }
}
