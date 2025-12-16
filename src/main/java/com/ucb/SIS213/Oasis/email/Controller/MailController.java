package com.ucb.SIS213.Oasis.email.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ucb.SIS213.Oasis.email.Model.MailStructure;
import com.ucb.SIS213.Oasis.email.Service.MailService;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

    @PostMapping("/send/{mail}")
    public ResponseDTO sendMail(@PathVariable("mail") String mail, @RequestBody MailStructure mailStructure) {
        try {
            LOGGER.info("=== RECIBIDA PETICIÓN DE ENVÍO DE CORREO ===");
            LOGGER.info("Destinatario: {}", mail);
            
            // Validar que el correo no esté vacío
            if (mail == null || mail.trim().isEmpty()) {
                LOGGER.error("El correo destinatario está vacío");
                return new ResponseDTO("MAIL-1001", "El correo destinatario no puede estar vacío");
            }

            // Validar formato de correo básico
            if (!mail.contains("@") || !mail.contains(".")) {
                LOGGER.error("Formato de correo inválido: {}", mail);
                return new ResponseDTO("MAIL-1006", "El formato del correo destinatario no es válido");
            }

            // Validar que el mailStructure no sea null
            if (mailStructure == null) {
                LOGGER.error("MailStructure es null");
                return new ResponseDTO("MAIL-1002", "La estructura del correo no puede estar vacía");
            }

            // Validar que el asunto y mensaje no estén vacíos
            if (mailStructure.getSubject() == null || mailStructure.getSubject().trim().isEmpty()) {
                LOGGER.error("El asunto del correo está vacío");
                return new ResponseDTO("MAIL-1003", "El asunto del correo no puede estar vacío");
            }

            if (mailStructure.getMessage() == null || mailStructure.getMessage().trim().isEmpty()) {
                LOGGER.error("El mensaje del correo está vacío");
                return new ResponseDTO("MAIL-1004", "El mensaje del correo no puede estar vacío");
            }

            LOGGER.info("Iniciando envío de correo...");
            mailService.sendMail(mail, mailStructure);
            LOGGER.info("=== CORREO ENVIADO EXITOSAMENTE ===");
            return new ResponseDTO("Correo enviado exitosamente");
        } catch (MessagingException e) {
            String errorMsg = "Error al enviar el correo: " + e.getMessage();
            if (e.getCause() != null) {
                errorMsg += " (Causa: " + e.getCause().getMessage() + ")";
            }
            LOGGER.error("Error de MessagingException al enviar correo a {}: {}", mail, errorMsg, e);
            
            // Mensajes de error más específicos
            String userFriendlyMessage = errorMsg;
            if (errorMsg.contains("Authentication failed") || errorMsg.contains("535")) {
                userFriendlyMessage = "Error de autenticación. Verifique las credenciales de correo configuradas.";
            } else if (errorMsg.contains("Connection") || errorMsg.contains("timeout")) {
                userFriendlyMessage = "Error de conexión con el servidor de correo. Verifique la configuración de red.";
            } else if (errorMsg.contains("Could not connect")) {
                userFriendlyMessage = "No se pudo conectar al servidor SMTP. Verifique SPRING_MAIL_HOST y SPRING_MAIL_PORT.";
            }
            
            return new ResponseDTO("MAIL-1000", userFriendlyMessage);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al enviar el correo a {}: {}", mail, e.getMessage(), e);
            return new ResponseDTO("MAIL-1005", "Error inesperado al enviar el correo: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseDTO testMailConfiguration() {
        try {
            LOGGER.info("=== PRUEBA DE CONFIGURACIÓN DE CORREO ===");
            
            // Verificar que el servicio esté disponible
            if (mailService == null) {
                LOGGER.error("MailService no está disponible (null)");
                return new ResponseDTO("MAIL-TEST-001", "MailService no está disponible");
            }
            
            LOGGER.info("MailService está disponible");
            return new ResponseDTO("Configuración de correo: MailService disponible. Verifique los logs para más detalles sobre la configuración SMTP.");
        } catch (Exception e) {
            LOGGER.error("Error al verificar configuración: {}", e.getMessage(), e);
            return new ResponseDTO("MAIL-TEST-003", 
                "Error inesperado: " + e.getMessage());
        }
    }
}
