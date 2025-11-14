package com.safelink.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "empresa_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String razao;

    @Column(nullable = false)
    private String nomeFantasia;

    @CNPJ
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String siteOficial;

    @Column(nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "telefone_id", nullable = false)
    private Telefone telefone;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.REMOVE)
    private Set<Link> links;
}

