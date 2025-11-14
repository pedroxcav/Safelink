package com.safelink.api.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Falha de validação nos dados enviados.");
    }

    public ValidationException(String message) {
        super(message);
    }
}