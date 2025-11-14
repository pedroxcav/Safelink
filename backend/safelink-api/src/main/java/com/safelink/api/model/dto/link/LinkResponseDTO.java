package com.safelink.api.model.dto.link;

import java.util.UUID;

public record LinkResponseDTO(
        UUID id,
        String linkReal,
        String linkEncurtado,
        UUID empresaId
) {}