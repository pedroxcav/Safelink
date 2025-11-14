package com.safelink.api.repository;

import com.safelink.api.model.DadoEnvolvido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DadoEnvolvidoRepository extends JpaRepository<DadoEnvolvido, UUID> {
}

