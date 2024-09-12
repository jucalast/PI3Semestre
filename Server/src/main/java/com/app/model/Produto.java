package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private Double preco;

    @ManyToMany
    @JoinTable(name = "produto_catefgoria", joinColumns = @JoinColumn(name = "peoduto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))

    private Set<Categoria> categorias;
}
