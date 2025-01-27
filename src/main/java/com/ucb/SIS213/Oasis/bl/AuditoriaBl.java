package com.ucb.SIS213.Oasis.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AuditoriaDao;
import com.ucb.SIS213.Oasis.entity.Auditoria;

import java.util.List;

@Service
public class AuditoriaBl {

    private AuditoriaDao auditoriaDao;

    @Autowired
    public AuditoriaBl(AuditoriaDao auditoriaDao) {
        this.auditoriaDao = auditoriaDao;
    }

    public List<Auditoria> getAllAuditoria() {
        return auditoriaDao.findAll();
    }

    public Auditoria getAuditoriaById(Long id) {
        Auditoria auditoria = auditoriaDao.findById(id).orElse(null);
        if (auditoria == null) {
            throw new RuntimeException("Auditoria does not exist");
        }
        return auditoria;
    }

    public Auditoria createAuditoria(Auditoria auditoria) {
        return auditoriaDao.save(auditoria);
    }

    public Auditoria updateAuditoria(Auditoria auditoria) {
        Auditoria auditoriaExistente = auditoriaDao.findById(auditoria.getIdAudit()).orElse(null);
        if (auditoriaExistente == null) {
            throw new RuntimeException("Auditoria does not exist");
        }
        return auditoriaDao.save(auditoria);
    }

    public void deleteAuditoria(Long id) {

        if (auditoriaDao.existsById(id)) {
            throw new RuntimeException("Auditoria does not exist");
        }
        auditoriaDao.deleteById(id);
    }
}
