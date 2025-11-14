package com.safelink.api.model.dto.empresa;

import com.safelink.api.model.dto.telefone.TelefoneRequestDTO;

import java.util.UUID;

public record EmpresaResponseDTO(
        UUID id,
        String razao,
        String nomeFantasia,
        String cnpj,
        String emailCorporativo,
        String siteOficial,
        String senha,
        TelefoneRequestDTO telefone
) {}