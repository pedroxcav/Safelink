package com.safelink.api.service;

import com.safelink.api.exception.NotFoundException;
import com.safelink.api.model.Cliente;
import com.safelink.api.model.Relato;
import com.safelink.api.model.dto.relato.NewRelatoDTO;
import com.safelink.api.model.dto.relato.RelatoDTO;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.repository.RelatoRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RelatoService {
    private final RelatoRepository relatoRepository;
    private final ClienteService clienteService;

    public RelatoService(RelatoRepository relatoRepository, ClienteService clienteService) {
        this.relatoRepository = relatoRepository;
        this.clienteService = clienteService;
    }

    public void createRelato(NewRelatoDTO data, JwtAuthenticationToken token) {
        Cliente cliente = clienteService.getCliente(token.getName());

        Relato relato = new Relato();
        relato.setTipoGolpe(data.tipoGolpe());
        relato.setCanal(data.tipoCanal());
        relato.setDescricao(data.descricao());
        relato.setData(data.date());
        relato.setTipoDado(data.tipoDado());
        relato.setInformacao(data.informacao());
        relato.setCliente(cliente);

        relatoRepository.save(relato);
    }

    public void deleteRelato(UUID id, JwtAuthenticationToken token) {
        Cliente cliente = clienteService.getCliente(token.getName());
        Relato relato = relatoRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (cliente.getRelatos().contains(relato))
            relatoRepository.delete(relato);
        else
            throw new NotFoundException("Relato não autorizado.");
    }

    public List<RelatoDTO> getRelatos(JwtAuthenticationToken token) {
        Cliente cliente = clienteService.getCliente(token.getName());
        List<Relato> relatos = cliente.getRelatos();
        return RelatoDTO.fromEntityList(relatos);
    }

    public List<RelatoDTO> getRelatoByTipoDado(TipoDado tipoDado) {
        List<Relato> relatos = relatoRepository.findByTipoDado(tipoDado);
        return RelatoDTO.fromEntityList(relatos);
    }

    public List<RelatoDTO> getRelatoByInformacao(TipoDado tipoDado, String informacao) {
        List<Relato> relatos = relatoRepository.findByTipoDadoAndInformacao(tipoDado, informacao);
        return RelatoDTO.fromEntityList(relatos);
    }
}