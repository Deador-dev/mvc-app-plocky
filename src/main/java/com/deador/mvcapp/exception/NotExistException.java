package com.deador.mvcapp.exception;

public class NotExistException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    private static final String NOT_EXIST_EXCEPTION = "Not exist";

    public NotExistException() {
        super(NOT_EXIST_EXCEPTION);
    }

    public NotExistException(String message) {
        super(message.isEmpty() ? NOT_EXIST_EXCEPTION : message);
    }
}
