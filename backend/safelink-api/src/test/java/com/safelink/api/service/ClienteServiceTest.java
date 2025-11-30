package com.safelink.api.service;

import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.exception.LoginFailedException;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Cliente;
import com.safelink.api.model.Telefone;
import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.cliente.ClienteDTO;
import com.safelink.api.controller.dto.cliente.NewClienteDTO;
import com.safelink.api.controller.dto.cliente.UpdateClienteDTO;
import com.safelink.api.model.enums.Role;
import com.safelink.api.repository.ClienteRepository;
import com.safelink.api.repository.TelefoneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private TelefoneRepository telefoneRepository;
    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private ClienteService service;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Jwt mockJwt(String subject) {
        Map<String, Object> headers = Map.of("alg", "none");
        Map<String, Object> claims = Map.of("sub", subject);
        Instant now = Instant.now();
        return new Jwt("tokenValue", now, now.plusSeconds(3600), headers, claims);
    }

    @Test
    @DisplayName("Criar cliente com dados válidos")
    void createCliente_ok() {
        NewClienteDTO dto = new NewClienteDTO(
                "Pedro",
                "teste@email.com",
                "12345678900",
                "senha",
                new NewTelefoneDTO("11", "999999999")
        );

        when(clienteRepository.existsByEmail(dto.email())).thenReturn(false);
        when(clienteRepository.existsByCpf(dto.cpf())).thenReturn(false);
        when(telefoneRepository.existsByDddAndNumero("11", "999999999")).thenReturn(false);
        when(encoder.encode("senha")).thenReturn("encoded");

        service.createCliente(dto);

        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Falha ao criar cliente com dados já existentes")
    void createCliente_usedData() {
        NewClienteDTO dto = new NewClienteDTO(
                "Pedro",
                "email@email.com",
                "12345678900",
                "senha",
                new NewTelefoneDTO("11", "999999999")
        );

        when(clienteRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(UsedDataException.class, () -> service.createCliente(dto));
    }

    @Test
    @DisplayName("Atualizar cliente com dados válidos")
    void updateCliente_ok() {
        UUID id = UUID.randomUUID();
        Cliente c = new Cliente();
        c.setId(id);
        c.setEmail("old@email.com");
        c.setSenha("old");
        c.setRole(Role.CLIENTE);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(c));
        when(clienteRepository.existsByEmail("novo@email.com")).thenReturn(false);
        when(encoder.encode("senhaNova")).thenReturn("encoded");

        UpdateClienteDTO dto = new UpdateClienteDTO("novo@email.com", "senhaNova");

        JwtAuthenticationToken token = new JwtAuthenticationToken(mockJwt(id.toString()));

        service.updateCliente(dto, token);

        verify(clienteRepository).save(c);
        assertEquals("novo@email.com", c.getEmail());
        assertEquals("encoded", c.getSenha());
    }

    @Test
    @DisplayName("Atualização falha quando email já existe")
    void updateCliente_emailExists() {
        UUID id = UUID.randomUUID();
        Cliente c = new Cliente();
        c.setId(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(c));
        when(clienteRepository.existsByEmail("dup@email.com")).thenReturn(true);

        UpdateClienteDTO dto = new UpdateClienteDTO("dup@email.com", "123");
        JwtAuthenticationToken token = new JwtAuthenticationToken(mockJwt(id.toString()));

        assertThrows(UsedDataException.class, () -> service.updateCliente(dto, token));
    }

    @Test
    @DisplayName("Buscar cliente retorna DTO corretamente")
    void getCliente_ok() {
        UUID id = UUID.randomUUID();

        Cliente c = new Cliente();
        c.setId(id);
        c.setNome("Pedro");
        c.setEmail("email@mail.com");
        c.setCpf("123");
        c.setRole(Role.CLIENTE);
        c.setTelefone(new Telefone("11", "999999999"));

        when(clienteRepository.findById(id)).thenReturn(Optional.of(c));

        JwtAuthenticationToken token = new JwtAuthenticationToken(mockJwt(id.toString()));

        ClienteDTO resp = service.getCliente(token);

        assertEquals(id, resp.id());
        assertEquals("Pedro", resp.nome());
        assertEquals("email@mail.com", resp.email());
    }

    @Test
    @DisplayName("Falha ao buscar cliente inexistente")
    void getCliente_notFound() {
        UUID id = UUID.randomUUID();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        JwtAuthenticationToken token = new JwtAuthenticationToken(mockJwt(id.toString()));

        assertThrows(NotFoundException.class, () -> service.getCliente(token));
    }

    @Test
    @DisplayName("Deletar cliente com sucesso")
    void deleteCliente_ok() {
        UUID id = UUID.randomUUID();
        Cliente c = new Cliente();
        c.setId(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(c));

        JwtAuthenticationToken token = new JwtAuthenticationToken(mockJwt(id.toString()));

        service.deleteCliente(token);

        verify(clienteRepository).delete(c);
    }

    @Test
    @DisplayName("Login com credenciais válidas")
    void login_ok() {
        UUID id = UUID.randomUUID();

        Cliente c = new Cliente();
        c.setId(id);
        c.setEmail("email@mail.com");
        c.setSenha("encoded");
        c.setRole(Role.CLIENTE);

        LoginRequestDTO req = new LoginRequestDTO("email@mail.com", "senha");

        when(clienteRepository.findByEmail("email@mail.com")).thenReturn(Optional.of(c));
        when(encoder.matches("senha", "encoded")).thenReturn(true);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("token123");
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        var resp = service.loginCliente(req);

        assertEquals("token123", resp.token());
        assertEquals(86400L, resp.expiresIn());
    }

    @Test
    @DisplayName("Login falha quando email não existe")
    void login_emailNotFound() {
        LoginRequestDTO req = new LoginRequestDTO("x@mail.com", "123");

        when(clienteRepository.findByEmail(req.email())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.loginCliente(req));
    }

    @Test
    @DisplayName("Login falha quando senha está incorreta")
    void login_wrongPassword() {
        Cliente c = new Cliente();
        c.setEmail("mail@mail.com");
        c.setSenha("encoded");

        LoginRequestDTO req = new LoginRequestDTO("mail@mail.com", "123");

        when(clienteRepository.findByEmail(req.email())).thenReturn(Optional.of(c));
        when(encoder.matches("123", "encoded")).thenReturn(false);

        assertThrows(LoginFailedException.class, () -> service.loginCliente(req));
    }
}
