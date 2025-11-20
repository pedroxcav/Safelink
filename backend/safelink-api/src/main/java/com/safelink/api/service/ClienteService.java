package com.safelink.api.service;

import com.safelink.api.exception.LoginFailedException;
import com.safelink.api.exception.NotFoundException;
import com.safelink.api.exception.UsedDataException;
import com.safelink.api.model.Cliente;
import com.safelink.api.model.Telefone;
import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.LoginResponseDTO;
import com.safelink.api.controller.dto.cliente.ClienteDTO;
import com.safelink.api.controller.dto.cliente.NewClienteDTO;
import com.safelink.api.controller.dto.cliente.UpdateClienteDTO;
import com.safelink.api.controller.dto.telefone.NewTelefoneDTO;
import com.safelink.api.model.enums.Role;
import com.safelink.api.repository.ClienteRepository;
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
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final TelefoneRepository telefoneRepository;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder encoder;

    public ClienteService(ClienteRepository clienteRepository, TelefoneRepository telefoneRepository, JwtEncoder jwtEncoder, PasswordEncoder encoder) {
        this.clienteRepository = clienteRepository;
        this.telefoneRepository = telefoneRepository;
        this.jwtEncoder = jwtEncoder;
        this.encoder = encoder;
    }

    public void createCliente(NewClienteDTO data) {
        boolean byEmail = clienteRepository.existsByEmail(data.email());
        boolean byCpf = clienteRepository.existsByCpf(data.cpf());

        NewTelefoneDTO telefoneData = data.telefone();
        boolean byTelefone = telefoneRepository.existsByDddAndNumero(telefoneData.ddd(), telefoneData.numero());

        if (byEmail || byCpf || byTelefone)
            throw new UsedDataException();

        Cliente cliente = new Cliente();
        cliente.setEmail(data.email());
        cliente.setCpf(data.cpf());
        cliente.setNome(data.nome());
        cliente.setSenha(encoder.encode(data.senha()));
        cliente.setRole(Role.CLIENTE);

        Telefone telefone = new Telefone(telefoneData.ddd(), telefoneData.numero());
        cliente.setTelefone(telefone);

        clienteRepository.save(cliente);
    }

    public void updateCliente(UpdateClienteDTO data, JwtAuthenticationToken token){
        Cliente cliente = this.getCliente(token.getName());

        if(clienteRepository.existsByEmail(data.email()))
            throw new UsedDataException("Email already exists");

        cliente.setEmail(data.email());
        cliente.setSenha(encoder.encode(data.senha()));

        clienteRepository.save(cliente);
    }

    public ClienteDTO getCliente(JwtAuthenticationToken token){
        Cliente cliente =  this.getCliente(token.getName());
        return ClienteDTO.toDTO(cliente);
    }

    public Cliente getCliente(String id) {
        return clienteRepository.findById(UUID.fromString(id))
                .orElseThrow(NotFoundException::new);
    }

    public void deleteCliente(JwtAuthenticationToken token){
        Cliente cliente = this.getCliente(token.getName());
        clienteRepository.delete(cliente);
    }

    public LoginResponseDTO loginCliente(LoginRequestDTO data){
        Cliente cliente = clienteRepository.findByEmail(data.email())
                .orElseThrow(NotFoundException::new);
        if(!encoder.matches(data.senha(), cliente.getSenha()))
            throw new LoginFailedException();

        var expiresIn = 86400L;
        var now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("safelink.api")
                .subject(cliente.getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", cliente.getRole())
                .issuedAt(now)
                .build();
        Jwt token = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return new LoginResponseDTO(token.getTokenValue(), expiresIn);
    }
}
