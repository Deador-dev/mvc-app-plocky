package com.deador.mvcapp.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {
    private static final String WRONG_AUTHENTICATION = "You are not authenticated";

    public UserAuthenticationException() {
        super(WRONG_AUTHENTICATION);
    }

    public UserAuthenticationException(String message) {
        super(message.isEmpty() ? WRONG_AUTHENTICATION : message);
    }
}
