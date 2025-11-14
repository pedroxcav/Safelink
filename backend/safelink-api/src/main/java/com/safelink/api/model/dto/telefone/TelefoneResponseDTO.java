package com.safelink.api.model.dto.telefone;

import java.util.UUID;

public record TelefoneResponseDTO(
        UUID id,
        String ddd,
        String numero
) {}
