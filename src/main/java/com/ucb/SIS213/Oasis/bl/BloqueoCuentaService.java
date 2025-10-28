package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.AdminDao;
import com.ucb.SIS213.Oasis.dao.ClienteDao;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BloqueoCuentaService {

    private final AdminDao adminDao;
    private final ClienteDao clienteDao;

    @Autowired
    public BloqueoCuentaService(AdminDao adminDao, ClienteDao clienteDao) {
        this.adminDao = adminDao;
        this.clienteDao = clienteDao;
    }

    // ============================================
    // BLOQUEO MANUAL
    // ============================================

    /**
     * Bloquea la cuenta de un cliente manualmente
     */
    @Transactional
    public void bloquearCliente(Long clienteId, String motivo) {
        Cliente cliente = clienteDao.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        cliente.setEstadoCuenta(false);
        cliente.setFechaBloqueo(LocalDateTime.now());
        cliente.setMotivoBloqueo(motivo != null ? motivo : "Bloqueado manualmente");
        
        clienteDao.save(cliente);
    }

    /**
     * Bloquea la cuenta de un admin manualmente
     */
    @Transactional
    public void bloquearAdmin(Long adminId, String motivo) {
        Admin admin = adminDao.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        
        admin.setEstadoCuenta(false);
        admin.setFechaBloqueo(LocalDateTime.now());
        admin.setMotivoBloqueo(motivo != null ? motivo : "Bloqueado manualmente");
        
        adminDao.save(admin);
    }

    // ============================================
    // DESBLOQUEO
    // ============================================

    /**
     * Desbloquea la cuenta de un cliente
     */
    @Transactional
    public void desbloquearCliente(Long clienteId) {
        Cliente cliente = clienteDao.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        cliente.setEstadoCuenta(true);
        cliente.setIntentosFallidos(0);
        cliente.setFechaBloqueo(null);
        cliente.setMotivoBloqueo(null);
        
        clienteDao.save(cliente);
    }

    /**
     * Desbloquea la cuenta de un admin
     */
    @Transactional
    public void desbloquearAdmin(Long adminId) {
        Admin admin = adminDao.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        
        admin.setEstadoCuenta(true);
        admin.setIntentosFallidos(0);
        admin.setFechaBloqueo(null);
        admin.setMotivoBloqueo(null);
        
        adminDao.save(admin);
    }

    // ============================================
    // REGISTRO DE INTENTOS FALLIDOS
    // ============================================

    /**
     * Registra un intento fallido de login para cliente
     * Bloquea automáticamente si llega al límite
     */
    @Transactional
    public IntentosResult registrarIntentoFallidoCliente(Long clienteId) {
        Cliente cliente = clienteDao.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        int intentos = (cliente.getIntentosFallidos() != null ? cliente.getIntentosFallidos() : 0) + 1;
        cliente.setIntentosFallidos(intentos);
        
        boolean bloqueado = false;
        if (intentos >= 5) {
            cliente.setEstadoCuenta(false);
            cliente.setFechaBloqueo(LocalDateTime.now());
            cliente.setMotivoBloqueo("Bloqueado automáticamente por 5 intentos fallidos");
            bloqueado = true;
        }
        
        clienteDao.save(cliente);
        
        return new IntentosResult(bloqueado, intentos, 5 - intentos);
    }

    /**
     * Registra un intento fallido de login para admin
     * Bloquea automáticamente si llega al límite
     */
    @Transactional
    public IntentosResult registrarIntentoFallidoAdmin(Long adminId) {
        Admin admin = adminDao.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        
        int intentos = (admin.getIntentosFallidos() != null ? admin.getIntentosFallidos() : 0) + 1;
        admin.setIntentosFallidos(intentos);
        
        boolean bloqueado = false;
        if (intentos >= 5) {
            admin.setEstadoCuenta(false);
            admin.setFechaBloqueo(LocalDateTime.now());
            admin.setMotivoBloqueo("Bloqueado automáticamente por 5 intentos fallidos");
            bloqueado = true;
        }
        
        adminDao.save(admin);
        
        return new IntentosResult(bloqueado, intentos, 5 - intentos);
    }

    // ============================================
    // REINICIO DE INTENTOS
    // ============================================

    /**
     * Reinicia el contador de intentos fallidos de un cliente al iniciar sesión exitosamente
     */
    @Transactional
    public void reiniciarIntentosCliente(Long clienteId) {
        Cliente cliente = clienteDao.findById(clienteId).orElse(null);
        if (cliente != null && cliente.getIntentosFallidos() != null && cliente.getIntentosFallidos() > 0) {
            cliente.setIntentosFallidos(0);
            clienteDao.save(cliente);
        }
    }

    /**
     * Reinicia el contador de intentos fallidos de un admin al iniciar sesión exitosamente
     */
    @Transactional
    public void reiniciarIntentosAdmin(Long adminId) {
        Admin admin = adminDao.findById(adminId).orElse(null);
        if (admin != null && admin.getIntentosFallidos() != null && admin.getIntentosFallidos() > 0) {
            admin.setIntentosFallidos(0);
            adminDao.save(admin);
        }
    }

    // ============================================
    // VERIFICACIÓN DE BLOQUEO
    // ============================================

    /**
     * Verifica si la cuenta de un cliente está bloqueada
     */
    public boolean estaBloqueadoCliente(Long clienteId) {
        Cliente cliente = clienteDao.findById(clienteId).orElse(null);
        return cliente != null && cliente.getEstadoCuenta() != null && !cliente.getEstadoCuenta();
    }

    /**
     * Verifica si la cuenta de un admin está bloqueada
     */
    public boolean estaBloqueadoAdmin(Long adminId) {
        Admin admin = adminDao.findById(adminId).orElse(null);
        return admin != null && admin.getEstadoCuenta() != null && !admin.getEstadoCuenta();
    }

    // ============================================
    // INFORMACIÓN DE BLOQUEO
    // ============================================

    /**
     * Obtiene información de bloqueo del cliente
     */
    public BloqueoInfo getInfoBloqueoCliente(Long clienteId) {
        Cliente cliente = clienteDao.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        return new BloqueoInfo(
                cliente.getEstadoCuenta() != null ? cliente.getEstadoCuenta() : true,
                cliente.getIntentosFallidos() != null ? cliente.getIntentosFallidos() : 0,
                cliente.getFechaBloqueo(),
                cliente.getMotivoBloqueo()
        );
    }

    /**
     * Obtiene información de bloqueo del admin
     */
    public BloqueoInfo getInfoBloqueoAdmin(Long adminId) {
        Admin admin = adminDao.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));
        
        return new BloqueoInfo(
                admin.getEstadoCuenta() != null ? admin.getEstadoCuenta() : true,
                admin.getIntentosFallidos() != null ? admin.getIntentosFallidos() : 0,
                admin.getFechaBloqueo(),
                admin.getMotivoBloqueo()
        );
    }

    // ============================================
    // CLASES INTERNAS (DTOs)
    // ============================================

    /**
     * DTO para el resultado de intentos
     */
    public static class IntentosResult {
        private boolean bloqueado;
        private int intentos;
        private int intentosRestantes;

        public IntentosResult(boolean bloqueado, int intentos, int intentosRestantes) {
            this.bloqueado = bloqueado;
            this.intentos = intentos;
            this.intentosRestantes = Math.max(0, intentosRestantes);
        }

        public boolean isBloqueado() { return bloqueado; }
        public int getIntentos() { return intentos; }
        public int getIntentosRestantes() { return intentosRestantes; }
    }

    /**
     * DTO para información de bloqueo
     */
    public static class BloqueoInfo {
        private boolean activa;
        private int intentosFallidos;
        private LocalDateTime fechaBloqueo;
        private String motivoBloqueo;

        public BloqueoInfo(boolean activa, int intentosFallidos, LocalDateTime fechaBloqueo, String motivoBloqueo) {
            this.activa = activa;
            this.intentosFallidos = intentosFallidos;
            this.fechaBloqueo = fechaBloqueo;
            this.motivoBloqueo = motivoBloqueo;
        }

        public boolean isActiva() { return activa; }
        public int getIntentosFallidos() { return intentosFallidos; }
        public LocalDateTime getFechaBloqueo() { return fechaBloqueo; }
        public String getMotivoBloqueo() { return motivoBloqueo; }
    }
}

