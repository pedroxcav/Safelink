package com.safelink.api.repository;

import com.safelink.api.model.Empresa;
import com.safelink.api.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {
    boolean existsByEmpresaAndLinkReal(Empresa empresa, String linkReal);

    Optional<Link> findByLinkReal(String linkReal);
}
