package com.app.notifications;

import com.app.entity.Email;
import com.app.entity.Event;
import com.app.repos.EmailRepo;
import com.app.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

    private final EmailSenderService emailSenderService;
    private final EventRepo eventRepo;
    private final EmailRepo emailRepo;

    @Autowired
    public JmsReceiver(EmailSenderService emailSenderService, EventRepo eventRepo, EmailRepo emailRepo) {
        this.emailSenderService = emailSenderService;
        this.eventRepo = eventRepo;
        this.emailRepo = emailRepo;
    }

    @JmsListener(destination = "eventbox", containerFactory = "myFactory")
    public void receiveEvent(Event event) {
        eventRepo.save(event);
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        emailSenderService.send(email);
        emailRepo.save(email);
    }
}
