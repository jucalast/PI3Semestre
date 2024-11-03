package com.app.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Representa um produto disponível na aplicação, que pode ser um café especial
 * ou um método de preparo. Contém informações como nome, descrição, preço,
 * imagem e quantidade em estoque.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
@Data
@Entity
@Table(name = "produto")
public class Produto {

    /**
     * Identificador único do produto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do produto
     */
    @NotNull(message = "O nome do produto não pode ser nulo")
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    @Column(nullable = false)
    private String nome;

    /**
     * Descrição do produto
     */
    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    @Column(length = 500)
    private String descricao;

    /**
     * Preço do produto
     */
    @NotNull(message = "O preço não pode ser nulo")
    @Column(nullable = false)
    private BigDecimal preco;

    /**
     * URL da imagem do produto
     */
    private String imagem;

    /**
     * Quantidade em estoque do produto
     */
    @JsonProperty("quantidade_estoque")
    @NotNull(message = "A quantidade em estoque não pode ser nula")
    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    /**
     * Avaliação do produto
     */
    private Integer avaliacao;

    /**
     * Relacionamento com o Café Especial
     */
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private CafeEspecial cafeEspecial;

    /**
     * Relacionamento com o Método de Preparo
     */
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private MetodoPreparo metodoPreparo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public CafeEspecial getCafeEspecial() {
        return cafeEspecial;
    }

    public void setCafeEspecial(CafeEspecial cafeEspecial) {
        this.cafeEspecial = cafeEspecial;
    }

    public MetodoPreparo getMetodoPreparo() {
        return metodoPreparo;
    }

    public void setMetodoPreparo(MetodoPreparo metodoPreparo) {
        this.metodoPreparo = metodoPreparo;
    }
}
