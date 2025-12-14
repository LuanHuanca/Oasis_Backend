package com.ucb.SIS213.Oasis.email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.email.Model.MailStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${spring.mail.host:smtp.gmail.com}")
    private String mailHost;

    @Value("${spring.mail.port:587}")
    private Integer mailPort;

    public void sendMail(String mail, MailStructure mailStructure) throws MessagingException {
        try {
            LOGGER.info("=== INICIO ENVÍO DE CORREO ===");
            LOGGER.info("Servidor SMTP: {}:{}", mailHost, mailPort);
            LOGGER.info("Correo remitente: {}", fromMail);
            LOGGER.info("Correo destinatario: {}", mail);
            LOGGER.info("Asunto: {}", mailStructure.getSubject());
            LOGGER.info("Longitud del mensaje: {} caracteres", 
                       mailStructure.getMessage() != null ? mailStructure.getMessage().length() : 0);

            // Validar que el mailSender esté configurado
            if (mailSender == null) {
                LOGGER.error("JavaMailSender no está configurado (es null)");
                throw new MessagingException("JavaMailSender no está configurado correctamente");
            }

            // Validar que fromMail esté configurado
            if (fromMail == null || fromMail.trim().isEmpty()) {
                LOGGER.error("El correo remitente (spring.mail.username) no está configurado");
                throw new MessagingException("El correo remitente no está configurado. Verifique SPRING_MAIL_USERNAME");
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromMail);
            helper.setTo(mail);
            helper.setSubject(mailStructure.getSubject());

            // Detectar si el mensaje contiene HTML
            String messageContent = mailStructure.getMessage();
            boolean isHtml = messageContent != null && 
                             (messageContent.contains("<html") || 
                              messageContent.contains("<h1") || 
                              messageContent.contains("<h2") || 
                              messageContent.contains("<p>") || 
                              messageContent.contains("<ul>") || 
                              messageContent.contains("<li>") ||
                              messageContent.contains("<div>") ||
                              messageContent.contains("<br>") ||
                              messageContent.contains("<br/>") ||
                              messageContent.contains("<strong>") ||
                              messageContent.contains("<img"));
            
            LOGGER.info("Tipo de mensaje: {}", isHtml ? "HTML" : "Texto plano");
            helper.setText(messageContent, isHtml);

            LOGGER.info("Enviando mensaje...");
            mailSender.send(message);
            LOGGER.info("=== CORREO ENVIADO EXITOSAMENTE ===");
            
        } catch (MessagingException e) {
            LOGGER.error("Error de MessagingException al enviar correo: {}", e.getMessage(), e);
            LOGGER.error("Causa: {}", e.getCause() != null ? e.getCause().getMessage() : "N/A");
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error inesperado al enviar correo: {}", e.getMessage(), e);
            throw new MessagingException("Error inesperado: " + e.getMessage(), e);
        }
    }
}
