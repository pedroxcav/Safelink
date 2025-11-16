package com.safelink.api.model.dto.empresa;

import com.safelink.api.model.Empresa;
import com.safelink.api.model.dto.telefone.TelefoneDTO;

import java.util.UUID;

public record EmpresaDTO(
        UUID id,
        String razao,
        String nomeFantasia,
        String cnpj,
        String email,
        String siteOficial,
        TelefoneDTO telefone) {

    public static EmpresaDTO toDTO (Empresa e) {
        TelefoneDTO tel = null;
        if (e.getTelefone() != null)
            tel = new TelefoneDTO(e.getTelefone().getId(), e.getTelefone().getDdd(), e.getTelefone().getNumero());
        return new EmpresaDTO(e.getId(), e.getRazao(), e.getNome(), e.getCnpj(), e.getEmail(), e.getSiteOficial(), tel);
    }
}