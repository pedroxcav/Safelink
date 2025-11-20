package com.safelink.api.service;

import com.safelink.api.exception.LoginFailedException;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.enums.Role;
import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.LoginResponseDTO;
import com.safelink.api.controller.dto.empresa.NewEmpresaDTO;
import com.safelink.api.controller.dto.empresa.UpdateEmpresaDTO;
import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.repository.EmpresaRepository;
import com.safelink.api.repository.TelefoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {
    private EmpresaRepository empresaRepository;
    private TelefoneRepository telefoneRepository;
    private TelefoneService telefoneService;
    private PasswordEncoder encoder;
    private JwtEncoder jwtEncoder;
    private EmpresaService service;

    @BeforeEach
    void setup() {
        empresaRepository = mock(EmpresaRepository.class);
        telefoneRepository = mock(TelefoneRepository.class);
        telefoneService = mock(TelefoneService.class);
        encoder = mock(PasswordEncoder.class);
        jwtEncoder = mock(JwtEncoder.class);
        service = new EmpresaService(jwtEncoder, encoder, empresaRepository, telefoneRepository, telefoneService);
    }

    @Test
    @DisplayName("Criar empresa com dados válidos")
    void createEmpresa_ok() {
        NewTelefoneDTO tel = new NewTelefoneDTO("11", "999999999");
        NewEmpresaDTO dto = new NewEmpresaDTO("Razao", "Nome", "12.345.678/0001-00",
                "teste@empresa.com", "site.com", "123", tel);

        when(empresaRepository.existsByEmail(dto.email())).thenReturn(false);
        when(empresaRepository.existsByCnpj(dto.cnpj())).thenReturn(false);
        when(empresaRepository.existsByRazao(dto.razao())).thenReturn(false);
        when(empresaRepository.existsBySite(dto.site())).thenReturn(false);
        when(telefoneRepository.existsByDddAndNumero("11", "999999999")).thenReturn(false);
        when(encoder.encode("123")).thenReturn("enc123");

        ArgumentCaptor<Empresa> captor = ArgumentCaptor.forClass(Empresa.class);

        service.createEmpresa(dto);

        verify(empresaRepository).save(captor.capture());
        Empresa e = captor.getValue();

        assertEquals("Razao", e.getRazao());
        assertEquals("Nome", e.getNome());
        assertEquals("12.345.678/0001-00", e.getCnpj());
        assertEquals("teste@empresa.com", e.getEmail());
        assertEquals("site.com", e.getSite());
        assertEquals("enc123", e.getSenha());
        assertEquals(Role.EMPRESA, e.getRole());
        assertNotNull(e.getTelefone());
    }

    @Test
    @DisplayName("Criar empresa com dados duplicados deve lançar exceção")
    void createEmpresa_dadosDuplicados() {
        NewEmpresaDTO dto = new NewEmpresaDTO("Razao", "Nome", "cnpj",
                "email@a.com", "site", "senha", new NewTelefoneDTO("11", "999999999"));

        when(empresaRepository.existsByEmail("email@a.com")).thenReturn(true);

        assertThrows(UsedDataException.class, () -> service.createEmpresa(dto));
    }

    @Test
    @DisplayName("Atualizar empresa com dados válidos")
    void updateEmpresa_ok() {
        UUID id = UUID.randomUUID();
        Empresa empresa = new Empresa();
        empresa.setId(id);
        empresa.setEmail("old@mail.com");
        empresa.setSenha("old");

        UpdateEmpresaDTO dto = new UpdateEmpresaDTO("Novo Nome", "novo@mail.com", "novoSite", "novaSenha");

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(empresaRepository.existsByEmail("novo@mail.com")).thenReturn(false);
        when(encoder.encode("novaSenha")).thenReturn("encNova");

        var token = new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(
                mockJwt(id.toString())
        );

        service.updateEmpresa(dto, token);

        assertEquals("Novo Nome", empresa.getNome());
        assertEquals("novo@mail.com", empresa.getEmail());
        assertEquals("novoSite", empresa.getSite());
        assertEquals("encNova", empresa.getSenha());

        verify(empresaRepository).save(empresa);
    }

    @Test
    @DisplayName("Atualização deve falhar quando email já existe")
    void updateEmpresa_emailExistente() {
        UUID id = UUID.randomUUID();
        Empresa empresa = new Empresa();
        empresa.setId(id);

        UpdateEmpresaDTO dto = new UpdateEmpresaDTO("Nome", "email@a.com", "s", "s2");

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(empresaRepository.existsByEmail("email@a.com")).thenReturn(true);

        var token = new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(
                mockJwt(id.toString())
        );

        assertThrows(UsedDataException.class, () -> service.updateEmpresa(dto, token));
    }

    @Test
    @DisplayName("Login deve retornar token válido")
    void loginEmpresa_ok() {
        Empresa empresa = new Empresa();
        empresa.setId(UUID.randomUUID());
        empresa.setEmail("a@b.com");
        empresa.setSenha("enc");
        empresa.setRole(Role.EMPRESA);

        when(empresaRepository.findByEmail("a@b.com")).thenReturn(Optional.of(empresa));
        when(encoder.matches("123", "enc")).thenReturn(true);

        var jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("token");
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        LoginResponseDTO resp = service.loginEmpresa(new LoginRequestDTO("a@b.com", "123"));

        assertEquals("token", resp.token());
    }

    @Test
    @DisplayName("Login deve falhar com senha incorreta")
    void loginEmpresa_senhaErrada() {
        Empresa empresa = new Empresa();
        empresa.setSenha("enc");

        when(empresaRepository.findByEmail("a@b.com")).thenReturn(Optional.of(empresa));
        when(encoder.matches("123", "enc")).thenReturn(false);

        assertThrows(LoginFailedException.class, () ->
                service.loginEmpresa(new LoginRequestDTO("a@b.com", "123")));
    }

    @Test
    @DisplayName("Buscar empresa inexistente deve lançar exceção")
    void getEmpresa_notFound() {
        UUID id = UUID.randomUUID();

        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getEmpresa(id.toString()));
    }

    private Jwt mockJwt(String subject) {
        Map<String, Object> headers = Map.of("alg", "none");
        Map<String, Object> claims = Map.of("sub", subject);
        Instant now = Instant.now();
        return new Jwt("tokenValue", now, now.plusSeconds(3600), headers, claims);
    }
}