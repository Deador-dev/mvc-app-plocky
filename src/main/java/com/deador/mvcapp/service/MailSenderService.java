package com.deador.mvcapp.service;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
