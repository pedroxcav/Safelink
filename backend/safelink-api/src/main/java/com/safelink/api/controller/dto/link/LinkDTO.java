package com.safelink.api.controller.dto.link;

import com.safelink.api.model.Link;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record LinkDTO(
        UUID id,
        String linkReal,
        String linkEncurtado
) {
    public static List<LinkDTO> fromEntityList(List<Link> links) {
        return links.stream()
                .map(LinkDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static LinkDTO fromEntity(Link link) {
        return new LinkDTO(
                link.getId(),
                link.getLinkReal(),
                link.getLinkEncurtado()
        );
    }
}