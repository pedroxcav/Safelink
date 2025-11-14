package com.safelink.api.model.dto.dadoEnvolvido;

import com.safelink.api.model.enums.TipoDado;

import java.util.UUID;

public record DadoEnvolvidoResponseDTO(
        UUID id,
        TipoDado tipoDado,
        String informacao
) {}