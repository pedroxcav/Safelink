package com.safelink.api.repository;

import com.safelink.api.model.Relato;
import com.safelink.api.model.enums.TipoCanal;
import com.safelink.api.model.enums.TipoGolpe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelatoRepository extends JpaRepository<Relato, UUID> {

    List<Relato> findByClienteId(UUID clienteId);

    List<Relato> findByTipoGolpe(TipoGolpe tipoGolpe);

    List<Relato> findByCanal(TipoCanal canal);
}
