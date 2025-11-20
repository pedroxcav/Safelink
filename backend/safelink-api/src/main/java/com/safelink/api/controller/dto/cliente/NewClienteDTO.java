package com.safelink.api.controller.dto.cliente;

import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record NewClienteDTO(
        @NotBlank String nome,
        @NotBlank @CPF String cpf,
        @NotBlank String email,
        @NotBlank String senha,
        NewTelefoneDTO telefone
) {
}
