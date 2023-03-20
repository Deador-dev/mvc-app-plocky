package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    private final ObjectFactory objectFactory;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailUsername;

    @Autowired
    public MailSenderServiceImpl(ObjectFactory objectFactory,
                                 JavaMailSender javaMailSender) {
        this.objectFactory = objectFactory;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = (SimpleMailMessage) objectFactory.createObject(SimpleMailMessage.class);
        mailMessage.setFrom(mailUsername);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
