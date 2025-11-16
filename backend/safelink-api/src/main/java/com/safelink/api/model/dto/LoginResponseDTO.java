package com.safelink.api.model.dto;

public record LoginResponseDTO(
        String token,
        Long expiresIn
) {}