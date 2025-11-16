package com.safelink.api.model.dto.empresa;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record UpdateEmpresaDTO(
        @NotBlank String razao,
        @NotBlank String nomeFantasia,
        @Email @NotBlank String email,
        @NotBlank String siteOficial,
        String senha,
        UUID telefoneId
) {}
