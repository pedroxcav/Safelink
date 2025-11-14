package com.safelink.api.repository;

import com.safelink.api.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {

    List<Link> findByEmpresaId(UUID empresaId);
}
