package com.safelink.api.service;

import com.safelink.api.service.client.LinkClient;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.Link;
import com.safelink.api.controller.dto.link.LinkDTO;
import com.safelink.api.controller.dto.link.NewLinkDTO;
import com.safelink.api.repository.LinkRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final EmpresaService empresaService;
    private final LinkClient linkClient;

    public LinkService(LinkRepository linkRepository, EmpresaService empresaService, LinkClient linkClient) {
        this.linkRepository = linkRepository;
        this.empresaService = empresaService;
        this.linkClient = linkClient;
    }

    public void createLink(NewLinkDTO data, JwtAuthenticationToken token) {
        Empresa empresa = empresaService.getEmpresa(token.getName());
        boolean byLinkReal = linkRepository.existsByEmpresaAndLinkReal(empresa, data.linkReal());

        if (!byLinkReal) {
            Link link = new Link();
            link.setLinkReal(data.linkReal());
            String shortenUrl = this.shortenUrl(data.linkReal());
            link.setLinkEncurtado(shortenUrl);
            link.setEmpresa(empresa);
            linkRepository.save(link);
        } else
            throw new UsedDataException("Link existente");
    }

    public void deleteLink(String linkReal, JwtAuthenticationToken token) {
        Link link = linkRepository.findByLinkReal(linkReal).orElseThrow(NotFoundException::new);
        Empresa empresa = empresaService.getEmpresa(token.getName());

        if(empresa.getLinks().contains(link))
            linkRepository.delete(link);
        else
            throw new NotFoundException("Link não autorizado.");
    }

    public List<LinkDTO> getLinks(JwtAuthenticationToken token) {
        Empresa empresa = empresaService.getEmpresa(token.getName());
        List<Link> links = empresa.getLinks();
        return LinkDTO.fromEntityList(links);
    }

    private String shortenUrl(String url) {
        return linkClient.shortenUrl(url);
    }
}
