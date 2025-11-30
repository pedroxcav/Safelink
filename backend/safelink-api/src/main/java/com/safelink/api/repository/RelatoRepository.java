package com.safelink.api.repository;

import com.safelink.api.model.Relato;
import com.safelink.api.model.enums.TipoDado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelatoRepository extends JpaRepository<Relato, UUID> {
    List<Relato> findByTipoDado(TipoDado tipoDado);
    List<Relato> findByTipoDadoAndInformacao(TipoDado tipoDado, String informacao);

    @Query(value = "SELECT informacao FROM relato_table WHERE tipo_dado = :tipoDado", nativeQuery = true)
    List<String> findInformacaoDinamicaNativo(@Param("tipoDado") String tipoDado);
}
