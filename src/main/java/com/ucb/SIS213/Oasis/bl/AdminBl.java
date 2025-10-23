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


    @Autowired
    public AdminBl(AdminDao adminDao, BCryptPasswordEncoder bCryptPasswordEncoder, AdminPermisoDAO rolPermisoDao, HistorialContrasenaService historialService) {
        this.adminDao = adminDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminPermisoDao = adminPermisoDao;
        this.historialService = historialService;
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
        String password = admin.getPassword();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(hashedPassword);
        return adminDao.save(admin);
    }

    public Admin login(String correo, String password) throws UserException {
        // Encontrar al cliente por correo
        Admin admin = adminDao.findByCorreo(correo);

        if (admin == null) {
            throw new UserException("Correo o contraseña incorrectos");
        }

        // Obtener la contraseña almacenada del cliente
        String hashedPassword = admin.getPassword();

        String mypassword = password +"Aqm,24Dla";

        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada después de ser hasheada
        if (!bCryptPasswordEncoder.matches(mypassword, hashedPassword)) {
            throw new UserException("Correo o contraseña incorrectos");
        }

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

        // update other fields
        adminExistente.setCorreo(admin.getCorreo());
        adminExistente.setIdPersona(admin.getIdPersona());
        adminExistente.setRol(admin.getRol());

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

}
