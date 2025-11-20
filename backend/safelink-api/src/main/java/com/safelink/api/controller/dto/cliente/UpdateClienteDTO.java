package com.safelink.api.controller.dto.cliente;

import jakarta.validation.constraints.NotBlank;

public record UpdateClienteDTO(
        @NotBlank String email,
        @NotBlank String senha
) {
}
