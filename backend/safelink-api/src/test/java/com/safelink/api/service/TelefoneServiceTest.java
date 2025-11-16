package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.model.Telefone;
import com.safelink.api.model.Usuario;
import com.safelink.api.model.dto.telefone.NewTelefoneDTO;
import com.safelink.api.model.dto.telefone.TelefoneDTO;
import com.safelink.api.repository.TelefoneRepository;
import com.safelink.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelefoneServiceTest {

    private TelefoneRepository telefoneRepository;
    private UsuarioRepository usuarioRepository;
    private TelefoneService service;

    @BeforeEach
    void setup() {
        telefoneRepository = mock(TelefoneRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        service = new TelefoneService(telefoneRepository, usuarioRepository);
    }

    @Test
    @DisplayName("Criar telefone com dados válidos")
    void createTelefone_ok() {
        NewTelefoneDTO dto = new NewTelefoneDTO("11", "988887777");
        Telefone tel = service.createTelefone(dto);

        assertEquals("11", tel.getDdd());
        assertEquals("988887777", tel.getNumero());
    }

    @Test
    @DisplayName("Atualizar telefone existente")
    void updateTelefone_ok() {
        UUID id = UUID.randomUUID();
        Telefone tel = new Telefone();
        tel.setId(id);

        TelefoneDTO dto = new TelefoneDTO(id, "21", "900000000");

        when(telefoneRepository.findById(id)).thenReturn(Optional.of(tel));

        service.updateTelefone(dto);

        assertEquals("21", tel.getDdd());
        assertEquals("900000000", tel.getNumero());
        verify(telefoneRepository).save(tel);
    }

    @Test
    @DisplayName("Atualizar telefone inexistente deve lançar exceção")
    void updateTelefone_notFound() {
        UUID id = UUID.randomUUID();
        TelefoneDTO dto = new TelefoneDTO(id, "11", "9999");

        when(telefoneRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateTelefone(dto));
    }

    @Test
    @DisplayName("Buscar telefone do usuário autenticado")
    void getTelefone_ok() {
        UUID id = UUID.randomUUID();
        Telefone tel = new Telefone("11", "999999999");
        tel.setId(UUID.randomUUID());

        class UsuarioFake extends Usuario {}
        Usuario u = new UsuarioFake();
        u.setId(id);
        u.setTelefone(tel);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(u));

        var token = new JwtAuthenticationToken(mockJwt(id.toString()));

        TelefoneDTO resp = service.getTelefone(token);

        assertEquals(tel.getId(), resp.id());
        assertEquals("11", resp.ddd());
    }

    @Test
    @DisplayName("Buscar telefone de usuário inexistente deve lançar exceção")
    void getTelefone_notFound() {
        UUID id = UUID.randomUUID();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        var token = new JwtAuthenticationToken(mockJwt(id.toString()));

        assertThrows(NotFoundException.class, () -> service.getTelefone(token));
    }

    private Jwt mockJwt(String subject) {
        Map<String, Object> headers = Map.of("alg", "none");
        Map<String, Object> claims = Map.of("sub", subject);
        Instant now = Instant.now();
        return new Jwt("tokenValue", now, now.plusSeconds(3600), headers, claims);
    }
}