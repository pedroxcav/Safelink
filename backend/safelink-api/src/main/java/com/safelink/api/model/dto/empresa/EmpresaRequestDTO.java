package com.safelink.api.model.dto.empresa;

import com.safelink.api.model.dto.telefone.TelefoneRequestDTO;

public record EmpresaRequestDTO(
        String razao,
        String nomeFantasia,
        String cnpj,
        String emailCorporativo,
        String siteOficial,
        String senha,
        TelefoneRequestDTO telefone
) {}