package com.safelink.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "telefone_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 3)
    private String ddd;

    @Column(nullable = false, length = 9)
    private String numero;

    @OneToOne(mappedBy = "telefone")
    private Usuario usuario;

    public Telefone(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }
}