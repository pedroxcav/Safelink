package com.safelink.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import com.safelink.api.model.enums.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "empresa_table")
@PrimaryKeyJoinColumn(name = "usuario_id")
@NoArgsConstructor @AllArgsConstructor
public class Empresa extends Usuario {

    @Column(nullable = false, unique = true)
    private String razao;

    @CNPJ
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(nullable = false,  unique = true)
    private String site;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.REMOVE)
    private List<Link> links;
}