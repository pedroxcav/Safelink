package com.safelink.api.model.dto;

public record LoginRequestDTO(
        String email,
        String senha
) {}