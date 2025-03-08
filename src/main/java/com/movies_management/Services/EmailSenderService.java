package com.movies_management.Services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String your_email;
    private final JavaMailSender mailSender;
    public EmailSenderService (JavaMailSender mailSender){
        this.mailSender=mailSender;
    }
    public void SendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom (your_email);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);

    }






}

