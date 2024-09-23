package com.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String origem;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String variedade;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String torrefacao;

    private String notasSensoriais;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String torra;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String beneficiamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_torra")
    @Temporal(TemporalType.DATE)
    private Date dataTorra;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_validade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;

    @Column(name = "recomendacoes_preparo")
    private String recomendacoesPreparo;

    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
