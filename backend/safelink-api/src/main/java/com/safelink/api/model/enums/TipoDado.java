package com.safelink.api.model.enums;

public enum TipoDado {
    SITE("Site"),
    TELEFONE("Telefone"),
    TRANSFERENCIA_PIX("PIX"),
    USUARIO("Usuario");

    private final String value;

    TipoDado(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}