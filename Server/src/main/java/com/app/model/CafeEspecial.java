package com.app.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem;
    private String tipo;
    private String torrefacao;
    private String notasSensoriais;
    private String torra;
    private String beneficiamento;
    private LocalDate dataTorra;
    private LocalDate dataValidade;
    private String recomendacoesPreparo;

    // Relacionamento muitos-para-muitos com Produto
    @ManyToMany(mappedBy = "cafeEspeciais")
    private Set<Produto> produtos;

    // Getters and setters
}
