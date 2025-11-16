package com.safelink.api.service;

import com.safelink.api.exception.LoginFailedException;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Empresa;
import com.safelink.api.model.Telefone;
import com.safelink.api.model.Usuario;
import com.safelink.api.model.dto.LoginRequestDTO;
import com.safelink.api.model.dto.LoginResponseDTO;
import com.safelink.api.model.dto.empresa.EmpresaDTO;
import com.safelink.api.model.dto.empresa.NewEmpresaDTO;
import com.safelink.api.model.dto.empresa.UpdateEmpresaDTO;
import com.safelink.api.model.dto.telefone.NewTelefoneDTO;
import com.safelink.api.model.dto.telefone.TelefoneDTO;
import com.safelink.api.model.enums.Role;
import com.safelink.api.repository.EmpresaRepository;
import com.safelink.api.repository.TelefoneRepository;
import com.safelink.api.repository.UsuarioRepository;
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
        if (empresaRepository.existsByCnpj(data.cnpj())) {
            throw new UsedDataException();
        } else {
            Empresa empresa = new Empresa();
            empresa.setCnpj(data.cnpj());
            empresa.setRazao(data.razao());
            empresa.setNome(data.nomeFantasia());
            empresa.setEmail(data.email());
            empresa.setSiteOficial(data.siteOficial());
            empresa.setSenha(encoder.encode(data.senha()));
            empresa.setRole(Role.EMPRESA);

            NewTelefoneDTO telefoneData = data.telefone();
            Telefone telefone = new Telefone(telefoneData.ddd(), telefoneData.numero());
            empresa.setTelefone(telefone);

            telefoneRepository.save(telefone);
            empresaRepository.save(empresa);
        }
    }

    public void updateEmpresa(UpdateEmpresaDTO data, JwtAuthenticationToken token){
        Empresa empresa = this.getEmpresa(token.getName());

        empresa.setRazao(data.razao());
        empresa.setNome(data.nomeFantasia());
        empresa.setEmail(data.email());
        empresa.setSiteOficial(data.siteOficial());
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
                .issuer("office.api")
                .subject(empresa.getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", empresa.getRole())
                .issuedAt(now)
                .build();
        Jwt token = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return new LoginResponseDTO(token.getTokenValue(), expiresIn);
    }
}