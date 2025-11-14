package com.safelink.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cliente_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @CPF
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "telefone_id", nullable = false)
    private Telefone telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private Set<Relato> relatos;
}
