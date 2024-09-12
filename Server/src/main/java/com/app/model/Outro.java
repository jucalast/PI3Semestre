package com.app.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "outro_produto")
public class Outro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria; // Ex: Utensílios, Acessórios
    private String descricao;

    // Relacionamento muitos-para-muitos com Produto
    @ManyToMany(mappedBy = "outrosProdutos")
    private Set<Produto> produtos;

    // Getters and setters
}
