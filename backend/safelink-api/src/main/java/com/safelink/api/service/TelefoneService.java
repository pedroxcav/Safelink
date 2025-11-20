package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.model.Telefone;
import com.safelink.api.model.Usuario;
import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.controller.dto.telefone.TelefoneDTO;
import com.safelink.api.controller.dto.telefone.UpdateTelefoneDTO;
import com.safelink.api.repository.TelefoneRepository;
import com.safelink.api.repository.UsuarioRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TelefoneService {
    private final TelefoneRepository telefoneRepository;
    private final UsuarioRepository usuarioRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, UsuarioRepository usuarioRepository) {
        this.telefoneRepository = telefoneRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Telefone createTelefone(NewTelefoneDTO data) {
        return new Telefone(data.ddd(), data.numero());
    }

    public void updateTelefone(UpdateTelefoneDTO data, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        Telefone telefone = usuario.getTelefone();
        telefone.setDdd(data.ddd());
        telefone.setNumero(data.numero());

        telefoneRepository.save(telefone);
    }

    public TelefoneDTO getTelefone(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        return TelefoneDTO.toDTO(usuario.getTelefone());
    }
}
