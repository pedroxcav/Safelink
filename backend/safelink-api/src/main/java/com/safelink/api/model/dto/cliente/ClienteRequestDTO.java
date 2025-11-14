package com.safelink.api.model.dto.cliente;

import com.safelink.api.model.dto.telefone.TelefoneRequestDTO;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String email,
        String senha,
        TelefoneRequestDTO telefone
) {}