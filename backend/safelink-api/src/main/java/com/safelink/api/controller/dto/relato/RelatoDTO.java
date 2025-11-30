package com.safelink.api.controller.dto.relato;

import com.safelink.api.model.Relato;
import com.safelink.api.model.enums.TipoCanal;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.model.enums.TipoGolpe;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record RelatoDTO(
        UUID id,
        TipoGolpe tipoGolpe,
        TipoCanal tipoCanal,
        String descricao,
        LocalDate date,
        TipoDado tipoDado,
        String informacao
) {
    public static List<RelatoDTO> fromEntityList(List<Relato> relatos) {
        return relatos.stream()
                .map(RelatoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static RelatoDTO fromEntity(Relato relato) {
        return new RelatoDTO(
                relato.getId(),
                relato.getTipoGolpe(),
                relato.getCanal(),
                relato.getDescricao(),
                relato.getData(),
                relato.getTipoDado(),
                relato.getInformacao()
        );
    }
}
