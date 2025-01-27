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

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
        try {
            mailService.sendMail(mail, mailStructure);
            return "Envio exitoso";
        } catch (MessagingException e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
