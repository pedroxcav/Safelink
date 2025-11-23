package com.safelink.api.controller;

import com.safelink.api.controller.dto.link.DeleteLinkDTO;
import com.safelink.api.controller.dto.link.LinkDTO;
import com.safelink.api.controller.dto.link.NewLinkDTO;
import com.safelink.api.model.Link;
import com.safelink.api.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<Void> createLink(@RequestBody @Valid NewLinkDTO data, JwtAuthenticationToken token) {
        linkService.createLink(data, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLink(@RequestBody @Valid DeleteLinkDTO data, JwtAuthenticationToken token) {
        linkService.deleteLink(data.linkReal(), token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<LinkDTO>> getLinks(JwtAuthenticationToken token) {
        List<LinkDTO> links = linkService.getLinks(token);
        return ResponseEntity.status(HttpStatus.OK).body(links);
    }
}
