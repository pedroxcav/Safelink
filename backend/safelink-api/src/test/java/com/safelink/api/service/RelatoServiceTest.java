package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.model.Cliente;
import com.safelink.api.model.Relato;
import com.safelink.api.model.enums.Role;
import com.safelink.api.model.Telefone;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.controller.dto.relato.RelatoDTO;
import com.safelink.api.repository.RelatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RelatoServiceTest {

    @Mock
    private RelatoRepository relatoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private RelatoService service;

    private Cliente cliente;
    private UUID clienteId;

    @BeforeEach
    void setup() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);

        Telefone telefone = new Telefone("11", "999999999");
        telefone.setId(UUID.randomUUID());

        clienteId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Pedro");
        cliente.setEmail("pedro@email.com");
        cliente.setSenha("123");
        cliente.setTelefone(telefone);
        cliente.setRole(Role.CLIENTE);
        cliente.setCpf("12345678901");
        cliente.setRelatos(new ArrayList<>());
    }

    private Jwt mockJwt(String subject) {
        return new Jwt(
                "token",
                null,
                null,
                Map.of("alg", "none"),
                Map.of("sub", subject)
        );
    }

    @Test
    @DisplayName("Deletar relato pertencente ao cliente")
    void deleteRelato_ok() {
        Relato relato = new Relato();
        relato.setId(UUID.randomUUID());
        relato.setCliente(cliente);

        cliente.getRelatos().add(relato);

        when(clienteService.getCliente(clienteId.toString())).thenReturn(cliente);
        when(relatoRepository.findById(relato.getId())).thenReturn(Optional.of(relato));

        var token = new JwtAuthenticationToken(mockJwt(clienteId.toString()));

        service.deleteRelato(relato.getId(), token);

        verify(relatoRepository).delete(relato);
    }

    @Test
    @DisplayName("Deletar relato que não pertence ao cliente deve lançar exceção")
    void deleteRelato_notOwner() {
        Relato relato = new Relato();
        relato.setId(UUID.randomUUID());
        relato.setCliente(new Cliente()); // outro cliente

        when(clienteService.getCliente(clienteId.toString())).thenReturn(cliente);
        when(relatoRepository.findById(relato.getId())).thenReturn(Optional.of(relato));

        var token = new JwtAuthenticationToken(mockJwt(clienteId.toString()));

        assertThrows(NotFoundException.class, () -> service.deleteRelato(relato.getId(), token));
        verify(relatoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deletar relato inexistente deve lançar exceção")
    void deleteRelato_notFound() {
        UUID id = UUID.randomUUID();

        when(clienteService.getCliente(clienteId.toString())).thenReturn(cliente);
        when(relatoRepository.findById(id)).thenReturn(Optional.empty());

        var token = new JwtAuthenticationToken(mockJwt(clienteId.toString()));

        assertThrows(NotFoundException.class, () -> service.deleteRelato(id, token));
    }

    @Test
    @DisplayName("Listar relatos do cliente autenticado")
    void getRelatos_ok() {
        Relato r1 = new Relato();
        r1.setId(UUID.randomUUID());
        r1.setCliente(cliente);

        Relato r2 = new Relato();
        r2.setId(UUID.randomUUID());
        r2.setCliente(cliente);

        cliente.getRelatos().add(r1);
        cliente.getRelatos().add(r2);

        when(clienteService.getCliente(clienteId.toString())).thenReturn(cliente);

        var token = new JwtAuthenticationToken(mockJwt(clienteId.toString()));

        List<RelatoDTO> result = service.getRelatos(token);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Buscar relatos por tipo de dado e informação")
    void getRelatoByInformacao_ok() {
        Relato r = new Relato();
        r.setId(UUID.randomUUID());

        when(relatoRepository.findByTipoDadoAndInformacao(TipoDado.TELEFONE, "11999999999"))
                .thenReturn(List.of(r));

        List<RelatoDTO> result = service.getRelatoByInformacao(TipoDado.TELEFONE, "11999999999");

        assertEquals(1, result.size());
    }
}