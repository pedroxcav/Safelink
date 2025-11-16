package com.safelink.api.model.dto.cliente;

import com.safelink.api.model.dto.telefone.TelefoneDTO;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String email,
        String senha,
        TelefoneDTO telefone
) {}