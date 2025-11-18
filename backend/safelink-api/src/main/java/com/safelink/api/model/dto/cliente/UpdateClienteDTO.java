package com.safelink.api.model.dto.cliente;

import jakarta.validation.constraints.NotBlank;

public record UpdateClienteDTO(
        @NotBlank String email,
        @NotBlank String senha
) {
}
