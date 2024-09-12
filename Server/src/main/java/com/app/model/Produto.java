package com.app.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagem;
    private Integer quantEstoque;
    private Integer avaliacao; // mudar para chave estrangeira de avaliação

    // Relacionamento muitos-para-muitos com Café Especial
    @ManyToMany
    @JoinTable(name = "produto_cafe_especial", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "cafe_especial_id"))
    private Set<CafeEspecial> cafeEspeciais;

    // Relacionamento muitos-para-muitos com Métodos de Preparo
    @ManyToMany
    @JoinTable(name = "produto_metodo_preparo", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "metodo_preparo_id"))
    private Set<MetodoPreparo> metodosPreparo;

    // Relacionamento muitos-para-muitos com Outros Produtos
    @ManyToMany
    @JoinTable(name = "produto_outro_produto", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "outro_produto_id"))
    private Set<Outro> outrosProdutos;

    // Getters and setters
}
