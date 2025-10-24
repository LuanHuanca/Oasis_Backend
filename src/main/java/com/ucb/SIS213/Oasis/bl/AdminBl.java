package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.exep.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.dao.AdminDao;
import com.ucb.SIS213.Oasis.dao.AdminPermisoDAO;
import com.ucb.SIS213.Oasis.dao.RolPermisoDao;
import com.ucb.SIS213.Oasis.entity.Admin;
import com.ucb.SIS213.Oasis.entity.AdminPermiso;
import com.ucb.SIS213.Oasis.entity.RolPermiso;

import java.util.List;

@Service
public class AdminBl {

    private final AdminDao adminDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminPermisoDAO adminPermisoDao;
    private final AdminPermisoBl adminPermisoBl;
    private final RolPermisoDao rolPermisoDao;

    // 🔹 Constructor con todas las dependencias necesarias
    @Autowired
    public AdminBl(AdminDao adminDao,
                   BCryptPasswordEncoder bCryptPasswordEncoder,
                   AdminPermisoDAO adminPermisoDao,
                   AdminPermisoBl adminPermisoBl,
                   RolPermisoDao rolPermisoDao) {
        this.adminDao = adminDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminPermisoDao = adminPermisoDao;
        this.adminPermisoBl = adminPermisoBl;
        this.rolPermisoDao = rolPermisoDao;
    }

    // 🔹 Obtener todos los administradores
    public List<Admin> getAllAdmin() {
        return adminDao.findAll();
    }

    // 🔹 Buscar un administrador por ID
    public Admin getAdminById(Long id) {
        Admin admin = adminDao.findById(id).orElse(null);
        if (admin == null) {
            throw new RuntimeException("El administrador no existe");
        }
        return admin;
    }

    //Crea un nuevo administrador
    public Admin createAdmin(Admin admin) {
        String password = admin.getPassword();

        // Encriptar la contraseña antes de guardarla
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(hashedPassword);

        // Guardar el nuevo admin en la base de datos
        Admin nuevoAdmin = adminDao.save(admin);

        //Asignar permisos por defecto según su rol
        asignarPermisosPorDefecto(nuevoAdmin);

        return nuevoAdmin;
    }

    // 🔹 Iniciar sesión de administrador
    public Admin login(String correo, String password) throws UserException {
        Admin admin = adminDao.findByCorreo(correo);

        if (admin == null) {
            throw new UserException("Correo o contraseña incorrectos");
        }

        String hashedPassword = admin.getPassword();
        String mypassword = password + "Aqm,24Dla"; // patrón adicional de seguridad

        if (!bCryptPasswordEncoder.matches(mypassword, hashedPassword)) {
            throw new UserException("Correo o contraseña incorrectos");
        }

        admin.setPassword(null); // No devolver la contraseña al frontend
        return admin;
    }

    // 🔹 Actualizar datos de un administrador existente
    public Admin updateAdmin(Admin admin) {
        Admin adminExistente = adminDao.findById(admin.getIdAdmin()).orElse(null);
        if (adminExistente == null) {
            throw new RuntimeException("El administrador no existe");
        }
        return adminDao.save(admin);
    }

    // 🔹 Eliminar un administrador por ID
    public void deleteAdmin(Long id) {
        if (!adminDao.existsById(id)) {
            throw new RuntimeException("El administrador no existe");
        }
        adminDao.deleteById(id);
    }

    // 🔹 Obtener todos los permisos asignados a un admin
    public List<AdminPermiso> getPermisosByAdminId(Long adminId) {
        return adminPermisoBl.getPermisosByAdminId(adminId);
    }

    // 🔹 NUEVO MÉTODO: asignar permisos por defecto según el rol
    public void asignarPermisosPorDefecto(Admin admin) {
        if (admin.getRol() == null) {
            System.out.println("El admin no tiene rol asignado, no se pueden asignar permisos.");
            return;
        }

        Integer idRol = admin.getRol().getIdRol();
        List<RolPermiso> lista = rolPermisoDao.findByRol_IdRol(idRol);

        for (RolPermiso rp : lista) {
            AdminPermiso ap = new AdminPermiso();
            ap.setAdmin(admin);
            ap.setPermiso(rp.getPermiso());
            adminPermisoDao.save(ap);
        }

        System.out.println("Permisos por defecto asignados al admin: " + admin.getCorreo());
    }
}
