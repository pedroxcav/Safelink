package com.safelink.api.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() { super("Falha ao realizar login."); }
    public LoginFailedException(String message) { super(message); }
}