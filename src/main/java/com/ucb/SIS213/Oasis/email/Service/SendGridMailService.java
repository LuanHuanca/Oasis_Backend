package com.ucb.SIS213.Oasis.email.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucb.SIS213.Oasis.email.Model.MailStructure;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio de correo usando SendGrid API REST (HTTPS)
 * Esta solución funciona en Railway porque usa HTTPS (puerto 443) en lugar de SMTP
 */
@Service
public class SendGridMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridMailService.class);
    private static final String SENDGRID_API_URL = "https://api.sendgrid.com/v3/mail/send";
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${sendgrid.api.key:}")
    private String sendGridApiKey;

    @Value("${sendgrid.from.email:}")
    private String fromEmail;

    @Value("${mail.provider:smtp}")
    private String mailProvider;

    public SendGridMailService() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void sendMail(String to, MailStructure mailStructure) throws MessagingException {
        // Solo usar SendGrid si está configurado
        if (!"sendgrid".equalsIgnoreCase(mailProvider) || sendGridApiKey == null || sendGridApiKey.isEmpty()) {
            throw new MessagingException("SendGrid no está configurado. Configure SENDGRID_API_KEY y MAIL_PROVIDER=sendgrid");
        }

        try {
            LOGGER.info("=== INICIO ENVÍO DE CORREO VÍA SENDGRID API ===");
            LOGGER.info("Correo remitente: {}", fromEmail);
            LOGGER.info("Correo destinatario: {}", to);
            LOGGER.info("Asunto: {}", mailStructure.getSubject());

            // Construir el cuerpo de la petición según formato SendGrid
            Map<String, Object> requestBody = new HashMap<>();
            
            // From
            Map<String, String> from = new HashMap<>();
            from.put("email", fromEmail);
            requestBody.put("from", from);
            
            // To
            List<Map<String, String>> toList = new ArrayList<>();
            Map<String, String> toEmail = new HashMap<>();
            toEmail.put("email", to);
            toList.add(toEmail);
            
            List<Map<String, Object>> personalizations = new ArrayList<>();
            Map<String, Object> personalization = new HashMap<>();
            personalization.put("to", toList);
            personalizations.add(personalization);
            requestBody.put("personalizations", personalizations);
            
            // Subject
            requestBody.put("subject", mailStructure.getSubject());
            
            // Content
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

            List<Map<String, String>> content = new ArrayList<>();
            Map<String, String> contentItem = new HashMap<>();
            contentItem.put("type", isHtml ? "text/html" : "text/plain");
            contentItem.put("value", messageContent);
            content.add(contentItem);
            requestBody.put("content", content);

            LOGGER.info("Tipo de mensaje: {}", isHtml ? "HTML" : "Texto plano");

            // Convertir a JSON
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Crear la petición HTTP
            RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                .url(SENDGRID_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + sendGridApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

            LOGGER.info("Enviando mensaje vía API REST de SendGrid...");
            
            // Ejecutar la petición
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Sin detalles";
                    LOGGER.error("Error al enviar correo vía SendGrid. Código: {}, Respuesta: {}", 
                                response.code(), errorBody);
                    throw new MessagingException("Error al enviar correo: " + errorBody);
                }

                LOGGER.info("Respuesta de SendGrid: Código {}", response.code());
                LOGGER.info("=== CORREO ENVIADO EXITOSAMENTE VÍA SENDGRID ===");
            }

        } catch (IOException e) {
            LOGGER.error("Error de IO al enviar correo vía SendGrid: {}", e.getMessage(), e);
            throw new MessagingException("Error de conexión: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al enviar correo vía SendGrid: {}", e.getMessage(), e);
            throw new MessagingException("Error inesperado: " + e.getMessage(), e);
        }
    }
}

