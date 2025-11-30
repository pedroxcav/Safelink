package com.safelink.api.controller.dto.empresa;

import com.safelink.api.model.Empresa;
import com.safelink.api.controller.dto.telefone.TelefoneDTO;

import java.util.UUID;

public record EmpresaDTO(
        UUID id,
        String razao,
        String nome,
        String cnpj,
        String email,
        String site,
        TelefoneDTO telefone) {

    public static EmpresaDTO toDTO (Empresa e) {
        TelefoneDTO tel = null;
        if (e.getTelefone() != null)
            tel = new TelefoneDTO(e.getTelefone().getId(), e.getTelefone().getDdd(), e.getTelefone().getNumero());
        return new EmpresaDTO(e.getId(), e.getRazao(), e.getNome(), e.getCnpj(), e.getEmail(), e.getSite(), tel);
    }
}