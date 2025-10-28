package com.ucb.SIS213.Oasis.api;

import com.ucb.SIS213.Oasis.bl.BloqueoCuentaService;
import com.ucb.SIS213.Oasis.bl.BloqueoCuentaService.BloqueoInfo;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bloqueo")
@CrossOrigin(origins = "*")
public class BloqueoCuentaAPI {

    @Autowired
    private BloqueoCuentaService bloqueoCuentaService;

    // ============================================
    // ENDPOINTS PARA CLIENTES
    // ============================================

    /**
     * Bloquear cuenta de cliente manualmente
     * POST /api/v1/bloqueo/cliente/{id}/bloquear
     */
    @PostMapping("/cliente/{id}/bloquear")
    public ResponseEntity<ResponseDTO> bloquearCliente(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        try {
            String motivo = (body != null && body.containsKey("motivo")) 
                    ? body.get("motivo") 
                    : "Bloqueado manualmente";
            bloqueoCuentaService.bloquearCliente(id, motivo);
            
            Map<String, Object> result = Map.of(
                "clienteId", id,
                "motivo", motivo,
                "bloqueado", true
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("BLOQUEO-500", "Error al bloquear cuenta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Desbloquear cuenta de cliente
     * POST /api/v1/bloqueo/cliente/{id}/desbloquear
     */
    @PostMapping("/cliente/{id}/desbloquear")
    public ResponseEntity<ResponseDTO> desbloquearCliente(@PathVariable Long id) {
        try {
            bloqueoCuentaService.desbloquearCliente(id);
            
            Map<String, Object> result = Map.of(
                "clienteId", id,
                "bloqueado", false,
                "intentosFallidos", 0
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("DESBLOQUEO-500", "Error al desbloquear cuenta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Obtener información de bloqueo del cliente
     * GET /api/v1/bloqueo/cliente/{id}/info
     */
    @GetMapping("/cliente/{id}/info")
    public ResponseEntity<ResponseDTO> getInfoBloqueoCliente(@PathVariable Long id) {
        try {
            BloqueoInfo info = bloqueoCuentaService.getInfoBloqueoCliente(id);
            
            Map<String, Object> result = Map.of(
                "clienteId", id,
                "activa", info.isActiva(),
                "bloqueada", !info.isActiva(),
                "intentosFallidos", info.getIntentosFallidos(),
                "fechaBloqueo", info.getFechaBloqueo() != null ? info.getFechaBloqueo().toString() : "null",
                "motivoBloqueo", info.getMotivoBloqueo() != null ? info.getMotivoBloqueo() : "N/A"
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("INFO-500", "Error al obtener información: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ============================================
    // ENDPOINTS PARA ADMINISTRADORES
    // ============================================

    /**
     * Bloquear cuenta de admin manualmente
     * POST /api/v1/bloqueo/admin/{id}/bloquear
     */
    @PostMapping("/admin/{id}/bloquear")
    public ResponseEntity<ResponseDTO> bloquearAdmin(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        try {
            String motivo = (body != null && body.containsKey("motivo")) 
                    ? body.get("motivo") 
                    : "Bloqueado manualmente";
            bloqueoCuentaService.bloquearAdmin(id, motivo);
            
            Map<String, Object> result = Map.of(
                "adminId", id,
                "motivo", motivo,
                "bloqueado", true
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("BLOQUEO-500", "Error al bloquear cuenta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Desbloquear cuenta de admin
     * POST /api/v1/bloqueo/admin/{id}/desbloquear
     */
    @PostMapping("/admin/{id}/desbloquear")
    public ResponseEntity<ResponseDTO> desbloquearAdmin(@PathVariable Long id) {
        try {
            bloqueoCuentaService.desbloquearAdmin(id);
            
            Map<String, Object> result = Map.of(
                "adminId", id,
                "bloqueado", false,
                "intentosFallidos", 0
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("DESBLOQUEO-500", "Error al desbloquear cuenta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Obtener información de bloqueo del admin
     * GET /api/v1/bloqueo/admin/{id}/info
     */
    @GetMapping("/admin/{id}/info")
    public ResponseEntity<ResponseDTO> getInfoBloqueoAdmin(@PathVariable Long id) {
        try {
            BloqueoInfo info = bloqueoCuentaService.getInfoBloqueoAdmin(id);
            
            Map<String, Object> result = Map.of(
                "adminId", id,
                "activa", info.isActiva(),
                "bloqueada", !info.isActiva(),
                "intentosFallidos", info.getIntentosFallidos(),
                "fechaBloqueo", info.getFechaBloqueo() != null ? info.getFechaBloqueo().toString() : "null",
                "motivoBloqueo", info.getMotivoBloqueo() != null ? info.getMotivoBloqueo() : "N/A"
            );
            ResponseDTO response = new ResponseDTO(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("INFO-500", "Error al obtener información: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

