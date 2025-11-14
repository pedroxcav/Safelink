package com.safelink.api.model.enums;

public enum Role {

    EMPRESA(1L),
    CLIENTE(2L);

    private final Long role;

    Role(Long role) {
        this.role = role;
    }

    public Long getRole() {
        return role;
    }
}