package com.safelink.api.model;

import com.safelink.api.model.enums.TipoDado;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "dado_envolvido_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DadoEnvolvido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDado tipoDado;

    @Column(nullable = false)
    private String informacao;

    @OneToOne(mappedBy = "dadoEnvolvido")
    private Relato relato;
}
