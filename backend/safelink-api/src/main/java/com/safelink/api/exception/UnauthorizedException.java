package com.safelink.api.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Acesso não autorizado.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}