package com.deador.mvcapp.exception;

public class SendMessageToEmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String SEND_MESSAGE_TO_EMAIL_EXCEPTION = "Send message to email exception";

    public SendMessageToEmailException() {
        super(SEND_MESSAGE_TO_EMAIL_EXCEPTION);
    }

    public SendMessageToEmailException(String message) {
        super(message.isEmpty() ? SEND_MESSAGE_TO_EMAIL_EXCEPTION : message);
    }
}
