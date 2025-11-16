package com.safelink.api.model.dto.cliente;

import com.safelink.api.model.dto.telefone.TelefoneDTO;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String cpf,
        String email,
        String senha,
        TelefoneDTO telefone
) {}