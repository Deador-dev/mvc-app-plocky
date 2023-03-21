package com.deador.mvcapp.exception;

import org.springframework.security.core.AuthenticationException;


public class UserNotActivatedException extends AuthenticationException {
    //    private static final long serialVersionUID = 1L;
    private static final String USER_NOT_ACTIVATED_EXCEPTION = "User not activated";

    public UserNotActivatedException() {
        super(USER_NOT_ACTIVATED_EXCEPTION);
    }

    public UserNotActivatedException(String message) {
        super(message.isEmpty() ? USER_NOT_ACTIVATED_EXCEPTION : message);
    }
}
