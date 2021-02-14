package com.app.notifications;

import com.app.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService  {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Email email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email.getReceiver());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        mailSender.send(message);
    }
}
