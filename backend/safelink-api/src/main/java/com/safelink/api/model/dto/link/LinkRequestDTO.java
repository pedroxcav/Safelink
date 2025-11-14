package com.safelink.api.model.dto.link;

import java.util.UUID;

public record LinkRequestDTO(
        String linkReal,
        String linkEncurtado,
        UUID empresaId
) {}