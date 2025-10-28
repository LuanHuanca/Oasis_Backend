package com.ucb.SIS213.Oasis.bl;

import com.ucb.SIS213.Oasis.dao.RolPermisoRepository;
import com.ucb.SIS213.Oasis.entity.Permiso;
import com.ucb.SIS213.Oasis.entity.Rol;
import com.ucb.SIS213.Oasis.entity.RolPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;

    @Autowired
    public RolPermisoService(RolPermisoRepository rolPermisoRepository) {
        this.rolPermisoRepository = rolPermisoRepository;
    }

    /**
     * Asigna un permiso predeterminado a un rol
     */
    @Transactional
    public RolPermiso asignarPermisoARol(Long rolId, Long permisoId) {
        // Verificar si ya existe la relación
        if (rolPermisoRepository.existsByRolIdRolAndPermisoIdPermiso(rolId, permisoId)) {
            throw new RuntimeException("El rol ya tiene asignado este permiso");
        }

        RolPermiso rolPermiso = new RolPermiso();
        Rol rol = new Rol();
        rol.setIdRol(rolId.intValue());
        rolPermiso.setRol(rol);
        
        Permiso permiso = new Permiso();
        permiso.setIdPermiso(permisoId);
        rolPermiso.setPermiso(permiso);
        
        rolPermiso.setFechaAsignacion(LocalDateTime.now());

        return rolPermisoRepository.save(rolPermiso);
    }

    /**
     * Obtiene todos los permisos predeterminados de un rol
     */
    public List<RolPermiso> getPermisosByRol(Long rolId) {
        return rolPermisoRepository.findPermisosByRolId(rolId);
    }

    /**
     * Obtiene solo los permisos (sin la relación) de un rol
     */
    public List<Permiso> getPermisosSoloByRol(Long rolId) {
        return rolPermisoRepository.findPermisosByRolId(rolId)
                .stream()
                .map(RolPermiso::getPermiso)
                .toList();
    }

    /**
     * Elimina un permiso específico de un rol
     */
    @Transactional
    public void removerPermisoDeRol(Long rolId, Long permisoId) {
        rolPermisoRepository.deleteByRolIdRolAndPermisoIdPermiso(rolId, permisoId);
    }

    /**
     * Asigna múltiples permisos a un rol
     */
    @Transactional
    public List<RolPermiso> asignarPermisosARol(Long rolId, List<Long> permisoIds) {
        return permisoIds.stream()
                .map(permisoId -> asignarPermisoARol(rolId, permisoId))
                .toList();
    }

    /**
     * Reemplaza todos los permisos de un rol con una nueva lista
     */
    @Transactional
    public List<RolPermiso> reemplazarPermisosDeRol(Long rolId, List<Long> permisoIds) {
        // Eliminar permisos existentes
        List<RolPermiso> permisosExistentes = rolPermisoRepository.findByRolIdRol(rolId);
        rolPermisoRepository.deleteAll(permisosExistentes);

        // Asignar nuevos permisos
        return asignarPermisosARol(rolId, permisoIds);
    }

    /**
     * Verifica si un rol tiene un permiso específico
     */
    public boolean rolTienePermiso(Long rolId, Long permisoId) {
        return rolPermisoRepository.existsByRolIdRolAndPermisoIdPermiso(rolId, permisoId);
    }

    /**
     * Obtiene todos los roles que tienen un permiso específico
     */
    public List<RolPermiso> getRolesConPermiso(Long permisoId) {
        return rolPermisoRepository.findAll().stream()
                .filter(rp -> rp.getPermiso().getIdPermiso().equals(permisoId))
                .toList();
    }
}
