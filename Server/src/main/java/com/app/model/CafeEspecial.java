package com.app.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Classe que representa um Café Especial.
 * Contém informações detalhadas sobre a origem, variedade, torrefação, notas
 * sensoriais, entre outras características do café.
 * Também possui um relacionamento muitos-para-muitos com a entidade Produto.
 */
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Origem do café, especificando a região ou país.
     */
    private String origem;

    /**
     * Variedade do café, como Arábica ou Robusta.
     */
    private String variedade;

    /**
     * Tipo de torrefação do café.
     */
    private String torrefacao;

    /**
     * Notas sensoriais do café, como sabor e aroma.
     */
    private String notasSensoriais;

    /**
     * Nível de torra do café, como clara, média ou escura.
     */
    private String torra;

    /**
     * Método de beneficiamento do café, como natural, lavado, etc.
     */
    private String beneficiamento;

    /**
     * Data de torra do café.
     */
    private LocalDate dataTorra;

    /**
     * Data de validade do café.
     */
    private LocalDate dataValidade;

    /**
     * Recomendações de preparo para o café, como método ou proporções.
     */
    private String recomendacoesPreparo;

    /**
     * Nível de acidez do café.
     */
    private String acidez;

    /**
     * Relacionamento muitos-para-muitos com Produto.
     * Um café especial pode estar associado a vários produtos, e um produto pode
     * ter vários cafés especiais.
     */
    @ManyToMany(mappedBy = "cafeEspeciais")
    private Set<Produto> produtos;

    // Getters and setters
}
