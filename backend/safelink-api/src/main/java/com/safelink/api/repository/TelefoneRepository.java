package com.safelink.api.repository;

import com.safelink.api.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
    boolean existsByDddAndNumero(String ddd, String numero);
}

