package com.safelink.api.model;

import com.safelink.api.model.enums.TipoCanal;
import com.safelink.api.model.enums.TipoDado;
import com.safelink.api.model.enums.TipoGolpe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "relato_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Relato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGolpe tipoGolpe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCanal canal;

    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDado tipoDado;

    @Column(nullable = false)
    private String informacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}