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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um produto na aplicação.
 * Contém informações sobre o nome, descrição, preço, imagem, quantidade em
 * estoque e avaliação do produto.
 * Além disso, possui relacionamentos muitos-para-muitos com outras entidades
 * como Café Especial, Método de Preparo e Outros Produtos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do produto.
     */
    private String nome;

    /**
     * Descrição detalhada do produto.
     */
    private String descricao;

    /**
     * Preço do produto.
     */
    private BigDecimal preco;

    /**
     * URL ou caminho da imagem associada ao produto.
     */
    private String imagem;

    /**
     * Quantidade disponível em estoque do produto.
     */
    private Integer quantEstoque;

    /**
     * Avaliação do produto. Esse valor poderá ser substituído futuramente por uma
     * chave estrangeira de avaliação.
     */
    private Integer avaliacao;

    /**
     * Relacionamento muitos-para-muitos com Café Especial.
     * Um produto pode ter vários cafés especiais associados, e um café especial
     * pode estar associado a vários produtos.
     */
    @ManyToMany
    @JoinTable(name = "produto_cafe_especial", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "cafe_especial_id"))
    private Set<CafeEspecial> cafeEspeciais;

    /**
     * Relacionamento muitos-para-muitos com Métodos de Preparo.
     * Um produto pode estar associado a vários métodos de preparo, como Prensa
     * Francesa, Hario V60, etc.
     * Um método de preparo também pode estar associado a vários produtos.
     */
    @ManyToMany
    @JoinTable(name = "produto_metodo_preparo", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "metodo_preparo_id"))
    private Set<MetodoPreparo> metodosPreparo;

    /**
     * Relacionamento muitos-para-muitos com Outros Produtos.
     * Representa a associação de produtos com categorias genéricas, como utensílios
     * ou acessórios.
     */
    @ManyToMany
    @JoinTable(name = "produto_outro_produto", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "outro_produto_id"))
    private Set<OutrosProdutos> outrosProdutos;

    // Getters and setters gerados automaticamente pelo Lombok (@Data)
}
