package com.safelink.api.model.dto.relato;

import com.safelink.api.model.dto.dadoEnvolvido.DadoEnvolvidoRequestDTO;
import com.safelink.api.model.enums.TipoCanal;
import com.safelink.api.model.enums.TipoGolpe;

import java.time.LocalDate;
import java.util.UUID;

public record RelatoRequestDTO(
        TipoGolpe tipoGolpe,
        TipoCanal canal,
        String descricao,
        LocalDate data,
        DadoEnvolvidoRequestDTO dadoEnvolvido,
        UUID clienteId
) {}