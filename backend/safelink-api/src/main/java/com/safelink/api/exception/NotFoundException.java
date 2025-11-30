package com.safelink.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() { super("O recurso não foi encontrado."); }
    public NotFoundException(String message) { super(message); }
}