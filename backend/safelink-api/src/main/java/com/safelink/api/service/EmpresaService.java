package com.safelink.api.service;

import com.safelink.api.exception.LoginFailedException;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.Telefone;
import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.LoginResponseDTO;
import com.safelink.api.controller.dto.empresa.EmpresaDTO;
import com.safelink.api.controller.dto.empresa.NewEmpresaDTO;
import com.safelink.api.controller.dto.empresa.UpdateEmpresaDTO;
import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.model.enums.Role;
import com.safelink.api.repository.EmpresaRepository;
import com.safelink.api.repository.TelefoneRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmpresaService {
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder encoder;
    private final EmpresaRepository empresaRepository;
    private final TelefoneRepository telefoneRepository;
    private final TelefoneService telefoneService;

    public EmpresaService(JwtEncoder jwtEncoder, PasswordEncoder encoder, EmpresaRepository empresaRepository, TelefoneRepository telefoneRepository, TelefoneService telefoneService) {
        this.jwtEncoder = jwtEncoder;
        this.encoder = encoder;
        this.empresaRepository = empresaRepository;
        this.telefoneRepository = telefoneRepository;
        this.telefoneService = telefoneService;
    }

    public void createEmpresa(NewEmpresaDTO data){
        boolean byEmail = empresaRepository.existsByEmail(data.email());
        boolean byCnpj = empresaRepository.existsByCnpj(data.cnpj());
        boolean byRazao = empresaRepository.existsByRazao(data.razao());
        boolean bySite =  empresaRepository.existsBySite(data.site());

        NewTelefoneDTO telefoneData = data.telefone();
        boolean byTelefone = telefoneRepository.existsByDddAndNumero(telefoneData.ddd(), telefoneData.numero());

        if (byEmail || byCnpj || byRazao || bySite || byTelefone)
            throw new UsedDataException();

        Empresa empresa = new Empresa();
        empresa.setCnpj(data.cnpj());
        empresa.setRazao(data.razao());
        empresa.setNome(data.nome());
        empresa.setEmail(data.email());
        empresa.setSite(data.site());
        empresa.setSenha(encoder.encode(data.senha()));
        empresa.setRole(Role.EMPRESA);

        Telefone telefone = new Telefone(telefoneData.ddd(), telefoneData.numero());
        empresa.setTelefone(telefone);

        empresaRepository.save(empresa);
    }

    public void updateEmpresa(UpdateEmpresaDTO data, JwtAuthenticationToken token){
        Empresa empresa = this.getEmpresa(token.getName());

        if(empresaRepository.existsByEmail(data.email()))
            throw new UsedDataException("Email already exists");

        empresa.setNome(data.nome());
        empresa.setEmail(data.email());
        empresa.setSite(data.site());
        empresa.setSenha(encoder.encode(data.senha()));

        empresaRepository.save(empresa);
    }

    public EmpresaDTO getEmpresa(JwtAuthenticationToken token){
        Empresa empresa =  this.getEmpresa(token.getName());
        return EmpresaDTO.toDTO(empresa);
    }

    public Empresa getEmpresa(String id) {
        return empresaRepository.findById(UUID.fromString(id))
                .orElseThrow(NotFoundException::new);
    }

    public void deleteEmpresa(JwtAuthenticationToken token){
        Empresa empresa = this.getEmpresa(token.getName());
        empresaRepository.delete(empresa);
    }

    public LoginResponseDTO loginEmpresa(LoginRequestDTO data){
        Empresa empresa = empresaRepository.findByEmail(data.email())
                .orElseThrow(NotFoundException::new);
        if(!encoder.matches(data.senha(), empresa.getSenha()))
            throw new LoginFailedException();

        var expiresIn = 86400L;
        var now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("safelink.api")
                .subject(empresa.getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", empresa.getRole())
                .issuedAt(now)
                .build();
        Jwt token = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return new LoginResponseDTO(token.getTokenValue(), expiresIn);
    }
}