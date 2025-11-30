package com.safelink.api.controller;

import com.safelink.api.controller.dto.telefone.TelefoneDTO;
import com.safelink.api.controller.dto.telefone.UpdateTelefoneDTO;
import com.safelink.api.service.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {
    private TelefoneService telefoneService;

    public TelefoneController(TelefoneService telefoneService) {this.telefoneService = telefoneService;}

    @PutMapping
    public ResponseEntity<Void> updateTelefone(@RequestBody @Valid UpdateTelefoneDTO data, JwtAuthenticationToken token) {
        telefoneService.updateTelefone(data, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<TelefoneDTO> getTelefone(JwtAuthenticationToken token) {
        TelefoneDTO data = this.telefoneService.getTelefone(token);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}