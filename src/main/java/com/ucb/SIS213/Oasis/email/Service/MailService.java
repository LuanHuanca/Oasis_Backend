package com.ucb.SIS213.Oasis.email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ucb.SIS213.Oasis.email.Model.MailStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String mail, MailStructure mailStructure) throws MessagingException {
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
        
        helper.setText(messageContent, isHtml);

        mailSender.send(message);
    }
}
