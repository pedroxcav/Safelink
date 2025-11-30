package com.safelink.api.controller.dto.empresa;

import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

public record NewEmpresaDTO(
        @NotBlank String razao,
        @NotBlank String nome,
        @CNPJ @NotBlank String cnpj,
        @Email @NotBlank String email,
        @NotBlank String site,
        @NotBlank String senha,
        NewTelefoneDTO telefone
) {}