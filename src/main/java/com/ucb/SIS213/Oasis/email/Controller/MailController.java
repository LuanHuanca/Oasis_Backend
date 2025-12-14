package com.ucb.SIS213.Oasis.email.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseDTO sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
        try {
            // Validar que el correo no esté vacío
            if (mail == null || mail.trim().isEmpty()) {
                LOGGER.error("El correo destinatario está vacío");
                return new ResponseDTO("MAIL-1001", "El correo destinatario no puede estar vacío");
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

            LOGGER.info("Intentando enviar correo a: {}", mail);
            mailService.sendMail(mail, mailStructure);
            LOGGER.info("Correo enviado exitosamente a: {}", mail);
            return new ResponseDTO("Correo enviado exitosamente");
        } catch (MessagingException e) {
            LOGGER.error("Error al enviar el correo a {}: {}", mail, e.getMessage(), e);
            return new ResponseDTO("MAIL-1000", "Error al enviar el correo: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error inesperado al enviar el correo a {}: {}", mail, e.getMessage(), e);
            return new ResponseDTO("MAIL-1005", "Error inesperado al enviar el correo: " + e.getMessage());
        }
    }
}
