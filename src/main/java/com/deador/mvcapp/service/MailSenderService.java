package com.deador.mvcapp.service;

public interface MailSenderService {
    boolean send(String emailTo, String subject, String message);
}
