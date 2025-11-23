package com.safelink.api.service;

import com.safelink.api.controller.dto.link.LinkDTO;
import com.safelink.api.controller.dto.link.NewLinkDTO;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.Link;
import com.safelink.api.repository.LinkRepository;
import com.safelink.api.service.client.LinkClient;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

class LinkServiceTest {
    private LinkRepository linkRepository;
    private EmpresaService empresaService;
    private LinkClient linkClient;
    private LinkService service;

    private Empresa empresa;
    private UUID empresaId;

    @BeforeEach
    void setup() {
        linkRepository = mock(LinkRepository.class);
        empresaService = mock(EmpresaService.class);
        linkClient = mock(LinkClient.class);
        service = new LinkService(linkRepository, empresaService, linkClient);

        empresaId = UUID.randomUUID();
        empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Empresa Teste");
        empresa.setLinks(new ArrayList<>());
    }

    private Jwt mockJwt(String subject) {
        return new Jwt(
                "token",
                null,
                null,
                java.util.Map.of("alg", "none"),
                java.util.Map.of("sub", subject)
        );
    }

    @Test
    @DisplayName("Criar link quando não existir")
    void createLink_ok() {
        NewLinkDTO dto = new NewLinkDTO("real");

        when(empresaService.getEmpresa(empresaId.toString())).thenReturn(empresa);
        when(linkRepository.existsByEmpresaAndLinkReal(empresa, "real")).thenReturn(false);
        when(linkClient.shortenUrl("real")).thenReturn("short");

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        service.createLink(dto, token);

        ArgumentCaptor<Link> captor = ArgumentCaptor.forClass(Link.class);
        verify(linkRepository).save(captor.capture());
        Link saved = captor.getValue();

        assertEquals("real", saved.getLinkReal());
        assertEquals("short", saved.getLinkEncurtado());
        assertEquals(empresa, saved.getEmpresa());
    }

    @Test
    @DisplayName("Criar link já existente deve lançar exceção")
    void createLink_existente() {
        NewLinkDTO dto = new NewLinkDTO("real");

        when(empresaService.getEmpresa(empresaId.toString())).thenReturn(empresa);
        when(linkRepository.existsByEmpresaAndLinkReal(empresa, "real")).thenReturn(true);

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        assertThrows(UsedDataException.class, () -> service.createLink(dto, token));
        verify(linkRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deletar link pertencente à empresa")
    void deleteLink_ok() {
        Link link = new Link();
        link.setLinkReal("real");
        link.setEmpresa(empresa);
        empresa.getLinks().add(link);

        when(empresaService.getEmpresa(empresaId.toString())).thenReturn(empresa);
        when(linkRepository.findByLinkReal("real")).thenReturn(Optional.of(link));

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        service.deleteLink("real", token);

        verify(linkRepository).delete(link);
    }

    @Test
    @DisplayName("Deletar link que não pertence à empresa deve lançar exceção")
    void deleteLink_notOwner() {
        Link link = new Link();
        link.setLinkReal("real");
        link.setEmpresa(new Empresa()); // outra empresa

        when(empresaService.getEmpresa(empresaId.toString())).thenReturn(empresa);
        when(linkRepository.findByLinkReal("real")).thenReturn(Optional.of(link));

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        assertThrows(NotFoundException.class, () -> service.deleteLink("real", token));
        verify(linkRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deletar link inexistente deve lançar exceção")
    void deleteLink_notFound() {
        when(linkRepository.findByLinkReal("real")).thenReturn(Optional.empty());

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        assertThrows(NotFoundException.class, () -> service.deleteLink("real", token));
    }

    @Test
    @DisplayName("Listar links da empresa autenticada")
    void getLinks_ok() {
        Link l1 = new Link();
        Link l2 = new Link();
        empresa.getLinks().add(l1);
        empresa.getLinks().add(l2);

        when(empresaService.getEmpresa(empresaId.toString())).thenReturn(empresa);

        var token = new JwtAuthenticationToken(mockJwt(empresaId.toString()));

        List<LinkDTO> result = service.getLinks(token);

        assertEquals(2, result.size());
    }
}