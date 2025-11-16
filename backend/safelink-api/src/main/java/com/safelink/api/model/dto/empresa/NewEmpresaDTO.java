package com.safelink.api.model.dto.empresa;

import com.safelink.api.model.dto.telefone.NewTelefoneDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

public record NewEmpresaDTO(
        @NotBlank String razao,
        @NotBlank String nomeFantasia,
        @CNPJ @NotBlank String cnpj,
        @Email @NotBlank String email,
        @NotBlank String siteOficial,
        @NotBlank String senha,
        NewTelefoneDTO telefone
) {}