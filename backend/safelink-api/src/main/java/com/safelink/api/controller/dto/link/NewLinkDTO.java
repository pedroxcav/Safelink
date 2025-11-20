package com.safelink.api.controller.dto.link;

import jakarta.validation.constraints.NotBlank;

public record NewLinkDTO(
        @NotBlank String linkReal
) {}