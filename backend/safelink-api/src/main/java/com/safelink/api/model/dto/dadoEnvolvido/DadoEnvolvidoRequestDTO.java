package com.safelink.api.model.dto.dadoEnvolvido;

import com.safelink.api.model.enums.TipoDado;

public record DadoEnvolvidoRequestDTO(
        TipoDado tipoDado,
        String informacao
) {}