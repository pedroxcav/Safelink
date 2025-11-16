package com.safelink.api.exception;

public class UsedDataException extends RuntimeException {
    public UsedDataException() { super("Dados já em uso."); }
    public UsedDataException(String message) { super(message); }
}