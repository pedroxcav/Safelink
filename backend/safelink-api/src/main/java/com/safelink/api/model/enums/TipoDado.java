package com.safelink.api.model.enums;

public enum TipoDado {
    CPF("CPF"),
    SENHA("Senha"),
    DADOS_CARTAO("Dados do cartão"),
    TRANSFERENCIA_PIX("Transferência do PIX"),
    EMAIL("E-mail"),
    OUTROS("Outros");

    private final String value;

    TipoDado(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}