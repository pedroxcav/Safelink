package com.safelink.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "cliente_table")
@PrimaryKeyJoinColumn(name = "usuario_id")
@NoArgsConstructor @AllArgsConstructor
public class Cliente extends Usuario {

    @CPF
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private List<Relato> relatos;
}
