package com.safelink.api.model.dto.telefone;

import jakarta.validation.constraints.NotNull;

public record UpdateTelefoneDTO(
        @NotNull String ddd,
        @NotNull String numero
) {}