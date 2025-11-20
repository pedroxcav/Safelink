package com.safelink.api.model.enums;

public enum TipoGolpe {
    GOLPE_DO_PRESENTE("Golpe do Presente"),
    PHISHING("Roubo de credenciais"),
    TAXA_ENTREGA("Taxa de entrega"),
    TRANSFERENCIA_PIX("Transferência PIX"),
    OUTRO("Outro");

    private final String value;

    TipoGolpe(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

