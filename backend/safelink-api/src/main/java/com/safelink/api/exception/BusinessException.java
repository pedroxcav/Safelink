package com.safelink.api.exception;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super("Erro de regra de negócio.");
    }

    public BusinessException(String message) {
        super(message);
    }
}