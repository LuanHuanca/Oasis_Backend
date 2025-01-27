package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.ComentariosDao;
import com.ucb.SIS213.Oasis.entity.Comentarios;

import java.util.List;

@Service
public class ComentariosBl {

    private ComentariosDao comentariosDao;

    @Autowired
    public ComentariosBl(ComentariosDao comentariosDao) {
        this.comentariosDao = comentariosDao;
    }

    public List<Comentarios> getAllComentarios() {
        return comentariosDao.findAll();
    }

    public Comentarios getComentariosById(Long id) {
        Comentarios comentarios = comentariosDao.findById(id).orElse(null);
        if (comentarios == null) {
            throw new RuntimeException("Comentarios does not exist");
        }
        return comentarios;
    }

    public Comentarios createComentarios(Comentarios comentarios) {
        return comentariosDao.save(comentarios);
    }

    public Comentarios updateComentariosById(Long id, Comentarios comentarios) {
        Comentarios comentariosActual = comentariosDao.findById(id).orElse(null);
        if (comentariosActual == null) {
            throw new RuntimeException("Comentarios does not exist");
        }
        comentariosActual.setComentario(comentarios.getComentario());
        return comentariosDao.save(comentariosActual);
    }

    public void deleteComentariosById(Long id) {
        Comentarios comentarios = comentariosDao.findById(id).orElse(null);
        if (comentarios == null) {
            throw new RuntimeException("Comentarios does not exist");
        }
        comentariosDao.delete(comentarios);
    }

}
