package com.safelink.api.controller;

import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.LoginResponseDTO;
import com.safelink.api.controller.dto.cliente.ClienteDTO;
import com.safelink.api.controller.dto.cliente.NewClienteDTO;
import com.safelink.api.controller.dto.cliente.UpdateClienteDTO;
import com.safelink.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data){
        LoginResponseDTO response = clienteService.loginCliente(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createCliente(@RequestBody @Valid NewClienteDTO data){
        clienteService.createCliente(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> getCliente(JwtAuthenticationToken token){
        ClienteDTO data = clienteService.getCliente(token);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PutMapping
    public ResponseEntity<Void> updateCliente(@RequestBody @Valid UpdateClienteDTO data, JwtAuthenticationToken token){
        clienteService.updateCliente(data, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCliente(JwtAuthenticationToken token){
        clienteService.deleteCliente(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
