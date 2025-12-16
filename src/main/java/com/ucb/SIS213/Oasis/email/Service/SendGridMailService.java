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
        // Validar configuración de SendGrid
        if (!"sendgrid".equalsIgnoreCase(mailProvider)) {
            throw new MessagingException("MAIL_PROVIDER no está configurado como 'sendgrid'. Configure MAIL_PROVIDER=sendgrid");
        }
        
        if (sendGridApiKey == null || sendGridApiKey.isEmpty() || sendGridApiKey.trim().isEmpty()) {
            LOGGER.error("SENDGRID_API_KEY no está configurada o está vacía");
            throw new MessagingException("SENDGRID_API_KEY no está configurada. Por favor, configure la variable de entorno SENDGRID_API_KEY con una API key válida de SendGrid.");
        }
        
        if (fromEmail == null || fromEmail.isEmpty() || fromEmail.trim().isEmpty()) {
            LOGGER.error("SENDGRID_FROM_EMAIL no está configurado o está vacío");
            throw new MessagingException("SENDGRID_FROM_EMAIL no está configurado. Por favor, configure la variable de entorno SENDGRID_FROM_EMAIL con el email remitente verificado en SendGrid.");
        }
        
        // Validar formato básico del email remitente
        if (!fromEmail.contains("@")) {
            LOGGER.error("SENDGRID_FROM_EMAIL tiene formato inválido: {}", fromEmail);
            throw new MessagingException("SENDGRID_FROM_EMAIL tiene formato inválido. Debe ser un email válido verificado en SendGrid.");
        }

        try {
            LOGGER.info("=== INICIO ENVÍO DE CORREO VÍA SENDGRID API ===");
            LOGGER.info("Correo remitente: {}", fromEmail);
            LOGGER.info("Correo destinatario: {}", to);
            LOGGER.info("Asunto: {}", mailStructure.getSubject());
            LOGGER.debug("API Key configurada: {} (longitud: {})", 
                        sendGridApiKey != null && !sendGridApiKey.isEmpty() ? "Sí" : "No",
                        sendGridApiKey != null ? sendGridApiKey.length() : 0);

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
                    
                    // Mensajes de error más específicos según el código HTTP
                    String userFriendlyMessage;
                    if (response.code() == 401) {
                        userFriendlyMessage = "Error de autenticación con SendGrid. La API key es inválida, expirada o revocada. " +
                                             "Por favor, verifica que SENDGRID_API_KEY esté correctamente configurada con una API key válida de SendGrid. " +
                                             "Puedes generar una nueva API key en: https://app.sendgrid.com/settings/api_keys";
                    } else if (response.code() == 403) {
                        userFriendlyMessage = "Acceso denegado por SendGrid. Verifica los permisos de tu API key y que el email remitente esté verificado.";
                    } else if (response.code() == 400) {
                        userFriendlyMessage = "Solicitud inválida a SendGrid. Verifica el formato del email y el contenido del mensaje.";
                    } else {
                        userFriendlyMessage = "Error al enviar correo vía SendGrid (Código: " + response.code() + "): " + errorBody;
                    }
                    
                    throw new MessagingException(userFriendlyMessage);
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

