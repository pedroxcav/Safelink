package com.safelink.api.controller.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record UpdateEmpresaDTO(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank String site,
        String senha
) {}
