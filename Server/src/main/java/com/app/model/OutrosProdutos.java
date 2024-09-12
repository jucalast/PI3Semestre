package com.app.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Classe que representa outros tipos de produtos além de cafés e métodos de
 * preparo.
 * Esses produtos podem incluir utensílios, acessórios e outros itens
 * complementares.
 * Possui um relacionamento muitos-para-muitos com a entidade Produto.
 */
@Entity
@Table(name = "outro_produto")
public class OutrosProdutos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Categoria do produto, que pode ser utensílios, acessórios, ou qualquer outra
     * categoria.
     */
    private String categoria;

    /**
     * Descrição do produto, fornecendo mais detalhes sobre o item.
     */
    private String descricao;

    /**
     * Relacionamento muitos-para-muitos com Produto.
     * Um produto pode estar associado a vários outros produtos, e um outro produto
     * pode estar associado a vários produtos.
     */
    @ManyToMany(mappedBy = "outrosProdutos")
    private Set<Produto> produtos;

    // Getters and setters
}
