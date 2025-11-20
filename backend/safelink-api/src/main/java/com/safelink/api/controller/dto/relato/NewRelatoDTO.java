package com.safelink.api.controller.dto.relato;

import com.safelink.api.model.enums.TipoCanal;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.model.enums.TipoGolpe;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record NewRelatoDTO(
        @NotBlank TipoGolpe tipoGolpe,
        @NotBlank TipoCanal tipoCanal,
        @NotBlank String descricao,
        @NotBlank LocalDate date,
        @NotBlank TipoDado tipoDado,
        @NotBlank String informacao
) {}
