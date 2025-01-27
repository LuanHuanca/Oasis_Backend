package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.PersonaDao;
import com.ucb.SIS213.Oasis.entity.Persona;

import java.util.List;

@Service
public class PersonaBl {
    private PersonaDao personaDao;

    @Autowired 
    public PersonaBl(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public List<Persona> getAllPersona() {
        return personaDao.findAll();
    }

    public Persona getPersonaById(Long id) {
        Persona persona = personaDao.findById(id).orElse(null);
        if (persona == null) {
            throw new RuntimeException("Persona does not exist");
        }
        return persona;
    }

    public Persona createPersona(Persona persona) {
        return personaDao.save(persona);
    }

    public Persona updatePersona(Long id, Persona persona) {
        Persona personaActual = personaDao.findById(id).orElse(null);
        if (personaActual == null) {
            throw new RuntimeException("Persona does not exist");
        }
        personaActual.setNombre(persona.getNombre());
        personaActual.setApellidoP(persona.getApellidoP());
        personaActual.setApellidoM(persona.getApellidoM());
        personaActual.setTelefono(persona.getTelefono());
        return personaDao.save(personaActual);
    }

    public void  deletePersona(Long id) {
        Persona persona = personaDao.findById(id).orElse(null);
        if (persona == null) {
            throw new RuntimeException("Persona does not exist");
        }
        personaDao.delete(persona);
    }

    public Long getLastIdPersona () {
        return personaDao.getLastInsertedPersonaId();
    }


}
