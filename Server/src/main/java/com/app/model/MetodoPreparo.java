package com.app.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "metodo_preparo")
public class MetodoPreparo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: Prensa Francesa, Hario V60
    private String descricao;

    // Relacionamento muitos-para-muitos com Produto
    @ManyToMany(mappedBy = "metodosPreparo")
    private Set<Produto> produtos;

    // Getters and setters
}
