package com.safelink.api.model;

import com.safelink.api.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "cliente_table")
@PrimaryKeyJoinColumn(name = "usuario_id")
@NoArgsConstructor @AllArgsConstructor
public class Cliente extends Usuario {

    @CPF
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private Set<Relato> relatos;
}
