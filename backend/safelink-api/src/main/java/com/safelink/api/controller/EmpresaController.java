package com.safelink.api.controller;

import com.safelink.api.controller.dto.LoginRequestDTO;
import com.safelink.api.controller.dto.LoginResponseDTO;
import com.safelink.api.controller.dto.empresa.EmpresaDTO;
import com.safelink.api.controller.dto.empresa.NewEmpresaDTO;
import com.safelink.api.controller.dto.empresa.UpdateEmpresaDTO;
import com.safelink.api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        LoginResponseDTO response = empresaService.loginEmpresa(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createEmpresa(@RequestBody @Valid NewEmpresaDTO data) {
        empresaService.createEmpresa(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<EmpresaDTO> getEmpresa(JwtAuthenticationToken token){
        EmpresaDTO data = empresaService.getEmpresa(token);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PutMapping
    public ResponseEntity<Void> updateEmpresa(@RequestBody @Valid UpdateEmpresaDTO data, JwtAuthenticationToken token){
        empresaService.updateEmpresa(data, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmpresa(JwtAuthenticationToken token){
        empresaService.deleteEmpresa(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}