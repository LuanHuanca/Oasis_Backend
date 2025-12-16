package com.ucb.SIS213.Oasis.bl;
import com.ucb.SIS213.Oasis.exep.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AdminDao;
import com.ucb.SIS213.Oasis.dao.AdminPermisoDAO;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;

import java.util.List;

@Service 
public class AdminBl {

    private AdminDao adminDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AdminPermisoDAO adminPermisoDao;
    private AdminPermisoBl adminPermisoBl;
    private HistorialContrasenaService historialService;
    private BloqueoCuentaService bloqueoCuentaService;


    @Autowired
    public AdminBl(AdminDao adminDao, BCryptPasswordEncoder bCryptPasswordEncoder, AdminPermisoDAO adminPermisoDao, 
                   HistorialContrasenaService historialService, BloqueoCuentaService bloqueoCuentaService) {
        this.adminDao = adminDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminPermisoDao = adminPermisoDao;
        this.historialService = historialService;
        this.bloqueoCuentaService = bloqueoCuentaService;
    }

    public List<Admin> getAllAdmin() {
        return adminDao.findAll();
    }

    public Admin getAdminById(Long id) {
        Admin admin = adminDao.findById(id).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Admin does not exist");
        }
        return admin;
    }

    public Admin createAdmin(Admin admin) {
        // Normalizar correo a minúsculas para consistencia
        if (admin.getCorreo() != null) {
            admin.setCorreo(admin.getCorreo().trim().toLowerCase());
        }
        
        // Validar que la contraseña no esté vacía
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña no puede estar vacía");
        }
        
        // Limpiar y normalizar la contraseña antes de aplicar el salt
        String passwordClean = admin.getPassword().trim();
        
        // Aplicar salt a la contraseña (consistente con login)
        String password = passwordClean + "Aqm,24Dla";
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(hashedPassword);
        
        // Inicializar campos de bloqueo de cuenta
        if (admin.getEstadoCuenta() == null) {
            admin.setEstadoCuenta(true); // Cuenta activa por defecto
        }
        if (admin.getIntentosFallidos() == null) {
            admin.setIntentosFallidos(0);
        }
        
        return adminDao.save(admin);
    }

    public Admin login(String correo, String password) throws UserException {
        // Normalizar correo y contraseña (trim y lowercase para consistencia)
        if (correo != null) {
            correo = correo.trim().toLowerCase();
        }
        if (password != null) {
            password = password.trim();
        }

        // Validar que correo y password no estén vacíos
        if (correo == null || correo.isEmpty()) {
            throw new UserException("El correo es requerido");
        }
        if (password == null || password.isEmpty()) {
            throw new UserException("La contraseña es requerida");
        }

        // Encontrar al admin por correo (case-insensitive)
        Admin admin = adminDao.findByCorreoIgnoreCase(correo);

        if (admin == null) {
            throw new UserException("Correo o contraseña incorrectos");
        }

        // ✅ VERIFICAR SI LA CUENTA ESTÁ BLOQUEADA
        if (admin.getEstadoCuenta() != null && !admin.getEstadoCuenta()) {
            throw new UserException("CUENTA_BLOQUEADA: Su cuenta ha sido bloqueada. " +
                    "Por favor, contacte a soporte o envíe un correo a soporte@oasis.com para su desbloqueo. " +
                    "Motivo: " + (admin.getMotivoBloqueo() != null ? admin.getMotivoBloqueo() : "No especificado"));
        }

        // Obtener la contraseña almacenada del admin
        String hashedPassword = admin.getPassword();
        
        // Validar que la contraseña almacenada no sea null
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new UserException("Error en la configuración de la cuenta. Contacte a soporte.");
        }

        // Aplicar el mismo salt que se usó al crear la contraseña
        String passwordWithSalt = password + "Aqm,24Dla";

        // Log de depuración (NO en producción con datos reales)
        // System.out.println("DEBUG - Password recibido: [" + password + "]");
        // System.out.println("DEBUG - Password con salt: [" + passwordWithSalt + "]");
        // System.out.println("DEBUG - Hash almacenado: [" + hashedPassword.substring(0, Math.min(20, hashedPassword.length())) + "...]");

        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
        boolean passwordMatches = bCryptPasswordEncoder.matches(passwordWithSalt, hashedPassword);
        
        if (!passwordMatches) {
            // ❌ Contraseña incorrecta - registrar intento fallido
            BloqueoCuentaService.IntentosResult resultado = bloqueoCuentaService.registrarIntentoFallidoAdmin(admin.getIdAdmin());
            
            if (resultado.isBloqueado()) {
                throw new UserException("CUENTA_BLOQUEADA: Su cuenta ha sido bloqueada por múltiples intentos fallidos. " +
                        "Contacte a soporte@oasis.com para desbloquearla.");
            } else {
                // No revelar información sobre intentos restantes para evitar que atacantes sepan que el correo existe
                throw new UserException("Correo o contraseña incorrectos");
            }
        }

        // ✅ Login exitoso - reiniciar intentos
        bloqueoCuentaService.reiniciarIntentosAdmin(admin.getIdAdmin());

        // No es necesario volver a hashear la contraseña al hacer el login
        admin.setPassword(null);

        return admin;
    }

    public Admin createNewAdmin(Admin admin) {
        return adminDao.save(admin);
    }

    public Admin updateAdmin(Admin admin) {
        Admin adminExistente = adminDao.findById(admin.getIdAdmin()).orElse(null);
        if (adminExistente == null) {
            throw new RuntimeException("Admin does not exist");
        }
        // If password is being updated, enforce history and save old hash
        String newPasswordRaw = admin.getPassword();
        if (newPasswordRaw != null && !newPasswordRaw.isBlank()) {
            String saltedNew = newPasswordRaw + "Aqm,24Dla";

            // Check current password
            if (bCryptPasswordEncoder.matches(saltedNew, adminExistente.getPassword())) {
                throw new RuntimeException("La nueva contraseña no puede ser igual a la actual");
            }

            int checkLast = 5;
            java.util.List<com.ucb.SIS213.Oasis.entity.HistorialContrasena> history = historialService.findHistoryForAdmin(adminExistente.getIdAdmin());

            int compared = 0;
            for (com.ucb.SIS213.Oasis.entity.HistorialContrasena h : history) {
                if (compared >= checkLast) break;
                if (bCryptPasswordEncoder.matches(saltedNew, h.getContrasenaHash())) {
                    throw new RuntimeException("La nueva contraseña ya fue usada anteriormente. Elija otra.");
                }
                compared++;
            }

            // Save current password hash to history before changing
            historialService.saveHistoryForAdmin(adminExistente.getIdAdmin(), adminExistente.getPassword());

            // Hash and set new password
            String hashed = bCryptPasswordEncoder.encode(saltedNew);
            adminExistente.setPassword(hashed);
        }

        // update other fields (solo si no son null)
        if (admin.getCorreo() != null && !admin.getCorreo().isBlank()) {
            adminExistente.setCorreo(admin.getCorreo());
        }
        if (admin.getIdPersona() != null) {
            adminExistente.setIdPersona(admin.getIdPersona());
        }
        if (admin.getRol() != null) {
            adminExistente.setRol(admin.getRol());
        }
        // Preservar campos de bloqueo si no se especifican
        if (admin.getEstadoCuenta() != null) {
            adminExistente.setEstadoCuenta(admin.getEstadoCuenta());
        }
        if (admin.getIntentosFallidos() != null) {
            adminExistente.setIntentosFallidos(admin.getIntentosFallidos());
        }
        if (admin.getFechaBloqueo() != null) {
            adminExistente.setFechaBloqueo(admin.getFechaBloqueo());
        }
        if (admin.getMotivoBloqueo() != null) {
            adminExistente.setMotivoBloqueo(admin.getMotivoBloqueo());
        }

        return adminDao.save(adminExistente);
    }

    public void deleteAdmin(Long id) {

        if (adminDao.existsById(id)) {
            throw new RuntimeException("Admin does not exist");
        }
        adminDao.deleteById(id);
    }

    public List<AdminPermiso> getPermisosByAdminId(Long adminId) {
        return adminPermisoBl.getPermisosByAdminId(adminId);
    }

    /**
     * Cambia la contraseña de un administrador específico
     * @param id ID del administrador
     * @param newPasswordRaw Nueva contraseña en texto plano
     * @return Admin actualizado
     */
    public Admin updatePassword(Long id, String newPasswordRaw) {
        Admin adminActual = adminDao.findById(id).orElse(null);
        if (adminActual == null) {
            throw new RuntimeException("Administrador no existe");
        }

        String saltedNew = newPasswordRaw + "Aqm,24Dla";

        // Validar contra contraseña actual
        if (bCryptPasswordEncoder.matches(saltedNew, adminActual.getPassword())) {
            throw new RuntimeException("La nueva contraseña no puede ser igual a la actual");
        }

        // Validar historial (últimas 5 contraseñas)
        int checkLast = 5;
        List<com.ucb.SIS213.Oasis.entity.HistorialContrasena> history = historialService.findHistoryForAdmin(adminActual.getIdAdmin());
        int compared = 0;
        for (com.ucb.SIS213.Oasis.entity.HistorialContrasena h : history) {
            if (compared >= checkLast) break;
            if (bCryptPasswordEncoder.matches(saltedNew, h.getContrasenaHash())) {
                throw new RuntimeException("La nueva contraseña ya fue usada anteriormente. Elija otra.");
            }
            compared++;
        }

        // Guardar contraseña actual en historial
        historialService.saveHistoryForAdmin(adminActual.getIdAdmin(), adminActual.getPassword());

        // Hashear y actualizar
        String hashed = bCryptPasswordEncoder.encode(saltedNew);
        adminActual.setPassword(hashed);

        return adminDao.save(adminActual);
    }

    /**
     * Valida la contraseña actual de un administrador
     * @param id ID del administrador
     * @param currentPassword Contraseña actual en texto plano
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public boolean validateCurrentPassword(Long id, String currentPassword) {
        Admin admin = adminDao.findById(id).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Administrador no existe");
        }

        String saltedCurrent = currentPassword + "Aqm,24Dla";
        return bCryptPasswordEncoder.matches(saltedCurrent, admin.getPassword());
    }

}
