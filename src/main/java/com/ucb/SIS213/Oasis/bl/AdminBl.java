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


    @Autowired
    public AdminBl(AdminDao adminDao, BCryptPasswordEncoder bCryptPasswordEncoder, AdminPermisoDAO rolPermisoDao) {
        this.adminDao = adminDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminPermisoDao = adminPermisoDao;
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
        System.out.println("Contraseña : " + admin.getPassword());
        String password = admin.getPassword();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(hashedPassword);
        System.out.println("Contraseña: " + admin.getPassword());
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

        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada después de ser hasheada
        if (!bCryptPasswordEncoder.matches(password, hashedPassword)) {
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
        return adminDao.save(admin);
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
