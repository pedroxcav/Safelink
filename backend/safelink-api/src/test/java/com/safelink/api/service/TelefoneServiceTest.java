package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.model.Telefone;
import com.safelink.api.model.Usuario;
import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.controller.dto.telefone.TelefoneDTO;
import com.safelink.api.controller.dto.telefone.UpdateTelefoneDTO;
import com.safelink.api.model.enums.Role;
import com.safelink.api.repository.TelefoneRepository;
import com.safelink.api.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TelefoneServiceTest {

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TelefoneService telefoneService;

    private Usuario usuario;
    private Telefone telefone;
    private UUID userId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        telefone = new Telefone("11", "999999999");

        usuario = new UsuarioFake(
                UUID.randomUUID(),
                "Pedro",
                "email@email.com",
                "senha",
                telefone,
                Role.CLIENTE
        );

        userId = usuario.getId();
    }

    static class UsuarioFake extends Usuario {
        public UsuarioFake(UUID id, String nome, String email, String senha, Telefone telefone, Role role) {
            super(id, nome, email, senha, telefone, role);
        }
    }

    @Test
    @DisplayName("createTelefone() → Deve criar um telefone corretamente")
    void testCreateTelefone() {
        NewTelefoneDTO dto = new NewTelefoneDTO("21", "888888888");

        Telefone result = telefoneService.createTelefone(dto);

        assertEquals("21", result.getDdd());
        assertEquals("888888888", result.getNumero());
    }

    @Test
    @DisplayName("updateTelefone() → Deve atualizar o telefone do usuário com sucesso")
    void testUpdateTelefoneSuccess() {
        UpdateTelefoneDTO dto = new UpdateTelefoneDTO("31", "777777777");

        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        telefoneService.updateTelefone(dto, token);

        assertEquals("31", telefone.getDdd());
        assertEquals("777777777", telefone.getNumero());
        verify(telefoneRepository, times(1)).save(telefone);
    }

    @Test
    @DisplayName("updateTelefone() → Deve lançar NotFoundException quando usuário não existir")
    void testUpdateTelefoneUserNotFound() {
        UpdateTelefoneDTO dto = new UpdateTelefoneDTO("31", "777777777");

        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> telefoneService.updateTelefone(dto, token)
        );

        verify(telefoneRepository, never()).save(any());
    }

    @Test
    @DisplayName("getTelefone() → Deve retornar o telefone do usuário corretamente")
    void testGetTelefoneSuccess() {
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        TelefoneDTO dto = telefoneService.getTelefone(token);

        assertEquals(telefone.getDdd(), dto.ddd());
        assertEquals(telefone.getNumero(), dto.numero());
    }

    @Test
    @DisplayName("getTelefone() → Deve lançar NotFoundException quando usuário não existe")
    void testGetTelefoneUserNotFound() {
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> telefoneService.getTelefone(token)
        );
    }
}