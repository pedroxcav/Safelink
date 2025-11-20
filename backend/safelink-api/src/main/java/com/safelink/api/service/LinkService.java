package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.Link;
import com.safelink.api.controller.dto.link.LinkDTO;
import com.safelink.api.controller.dto.link.NewLinkDTO;
import com.safelink.api.controller.dto.link.UpdateLinkDTO;
import com.safelink.api.repository.LinkRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final EmpresaService empresaService;

    public LinkService(LinkRepository linkRepository, EmpresaService empresaService) {
        this.linkRepository = linkRepository;
        this.empresaService = empresaService;
    }

    public void createLink(NewLinkDTO data, JwtAuthenticationToken token) {
        Empresa empresa = empresaService.getEmpresa(token.getName());
        boolean byLinkReal = linkRepository.existsByEmpresaAndLinkReal(empresa, data.linkReal());

        if (!byLinkReal) {
            Link link = new Link();
            link.setLinkReal(data.linkReal());
            link.setLinkEncurtado("LINK AQUI");
            link.setEmpresa(empresa);
            linkRepository.save(link);
        } else
            throw new UsedDataException("Link existente");
    }

    public void deleteLink(UUID id, JwtAuthenticationToken token) {
        Link link = linkRepository.findById(id).orElseThrow(NotFoundException::new);
        Empresa empresa = empresaService.getEmpresa(token.getName());

        if(empresa.getLinks().contains(link))
            linkRepository.delete(link);
        else
            throw new NotFoundException("Link não autorizado.");
    }

    public void updateLink(UUID id, UpdateLinkDTO data, JwtAuthenticationToken token) {
        Empresa empresa = empresaService.getEmpresa(token.getName());
        Link link = linkRepository.findById(id).orElseThrow(NotFoundException::new);
        boolean byLinkReal = linkRepository.existsByEmpresaAndLinkReal(empresa, data.linkReal());

        if(!byLinkReal && empresa.getLinks().contains(link)) {
            link.setLinkReal(data.linkReal());
            link.setLinkEncurtado("LINK AQUI");
            linkRepository.save(link);
        } else
            throw new NotFoundException("Link não autorizado.");
    }

    public List<LinkDTO> getLinks(JwtAuthenticationToken token) {
        Empresa empresa = empresaService.getEmpresa(token.getName());
        List<Link> links = empresa.getLinks();
        return LinkDTO.fromEntityList(links);
    }
}
