package com.safelink.api.controller;

import com.safelink.api.controller.dto.ActionGuideDTO;
import com.safelink.api.controller.dto.RankingItemDTO;
import com.safelink.api.controller.dto.relato.NewRelatoDTO;
import com.safelink.api.controller.dto.relato.RelatoDTO;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.service.RelatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/relato")
public class RelatoController {
    private final RelatoService relatoService;

    public RelatoController(RelatoService relatoService) {
        this.relatoService = relatoService;
    }

    @PostMapping
    public ResponseEntity<ActionGuideDTO> createRelato(@RequestBody NewRelatoDTO data, JwtAuthenticationToken token){
        ActionGuideDTO guide = relatoService.createRelato(data, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(guide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelato(@PathVariable UUID id, JwtAuthenticationToken token){
        relatoService.deleteRelato(id, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<RelatoDTO>> getRelatos(JwtAuthenticationToken token){
        List<RelatoDTO> relatos = relatoService.getRelatos(token);
        return ResponseEntity.status(HttpStatus.OK).body(relatos);
    }

    @GetMapping("/dado")
    public ResponseEntity<List<RankingItemDTO>> getRelatosByTipoDado(@RequestParam("tipo") TipoDado tipoDado) {
        List<RankingItemDTO> sortedInformationDTO = relatoService.getRelatoByTipoDado(tipoDado);
        return ResponseEntity.status(HttpStatus.OK).body(sortedInformationDTO);
    }

    @GetMapping("/verifica")
    public ResponseEntity<List<RelatoDTO>> getRelatosByTipoDadoAndInformacao(@RequestParam("tipo") TipoDado tipoDado, @RequestParam("valor") String valor) {
        List<RelatoDTO> relatos = relatoService.getRelatoByInformacao(tipoDado, valor);
        return ResponseEntity.status(HttpStatus.OK).body(relatos);
    }
}