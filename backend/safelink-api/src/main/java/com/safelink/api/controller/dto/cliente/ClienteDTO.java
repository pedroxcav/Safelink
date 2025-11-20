package com.safelink.api.controller.dto.cliente;

import com.safelink.api.model.Cliente;
import com.safelink.api.controller.dto.telefone.TelefoneDTO;

import java.util.UUID;

public record ClienteDTO(
        UUID id,
        String nome,
        String cpf,
        String email,
        TelefoneDTO telefone
) {
    public static ClienteDTO toDTO (Cliente e) {
        TelefoneDTO tel = null;
        if (e.getTelefone() != null)
            tel = new TelefoneDTO(e.getTelefone().getId(), e.getTelefone().getDdd(), e.getTelefone().getNumero());
        return new ClienteDTO(e.getId(), e.getNome(), e.getCpf(), e.getEmail(), tel);
    }
}
