package com.deador.mvcapp.exception;

public class AlreadyExistException extends IllegalStateException{
    private static final long serialVersionUID = 1L;
    private static final String ALREADY_EXIST_EXCEPTION = "Already exist";

    public AlreadyExistException() {
        super(ALREADY_EXIST_EXCEPTION);
    }

    public AlreadyExistException(String message) {
        super(message.isEmpty() ? ALREADY_EXIST_EXCEPTION : message);
    }
}
