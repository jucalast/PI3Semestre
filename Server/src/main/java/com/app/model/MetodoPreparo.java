package com.app.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Classe que representa um método de preparo de café, como Prensa Francesa ou
 * Hario V60.
 * Cada método possui um nome, uma descrição e o tipo de preparo.
 * Relaciona-se com a entidade Produto, permitindo que um método de preparo seja
 * usado em vários produtos e vice-versa.
 */
@Entity
@Table(name = "metodo_preparo")
public class MetodoPreparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do método de preparo, como Prensa Francesa, Hario V60, etc.
     */
    private String nome;

    /**
     * Descrição detalhada do método de preparo, explicando como ele funciona e suas
     * características.
     */
    private String descricao;

    /**
     * Tipo de preparo, que pode especificar detalhes adicionais, como se é manual,
     * elétrico, etc.
     */
    private String tipoPreparo;

    /**
     * Relacionamento muitos-para-muitos com Produto.
     * Um método de preparo pode estar associado a vários produtos, e um produto
     * pode utilizar vários métodos de preparo.
     */
    @ManyToMany(mappedBy = "metodosPreparo")
    private Set<Produto> produtos;

    // Getters and setters
}
