package com.safelink.api.controller.dto;

public record LoginResponseDTO(
        String token,
        Long expiresIn
) {}