package com.safelink.api.repository;

import com.safelink.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}

