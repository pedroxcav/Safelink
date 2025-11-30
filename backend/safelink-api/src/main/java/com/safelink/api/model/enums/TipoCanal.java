package com.safelink.api.model.enums;

public enum TipoCanal {
    WHATSAPP("Whatsapp"),
    SMS("SMS"),
    INSTAGRAM("Instagram"),
    WEB("Web");

    private final String value;

    TipoCanal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}