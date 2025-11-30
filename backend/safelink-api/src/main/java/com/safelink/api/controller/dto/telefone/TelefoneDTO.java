package com.safelink.api.controller.dto.telefone;

import com.safelink.api.model.Telefone;

import java.util.UUID;

public record TelefoneDTO(
        UUID id,
        String ddd,
        String numero) {

    public static TelefoneDTO toDTO(Telefone telefone) {
        return new TelefoneDTO(telefone.getId(), telefone.getDdd(), telefone.getNumero());
    }
}
