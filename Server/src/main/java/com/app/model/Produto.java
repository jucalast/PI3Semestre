package com.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@Table(name = "produto")
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * imagem do produto
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "imagem_base64")
    @Lob
    @OrderColumn(name = "imagem_order")
    private List<String> imagens = new ArrayList<>();

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
     * Indica se o produto está ativo.
     */
    @Column(nullable = false)
    private boolean ativo = true;

    /**
     * Relacionamento com o Café Especial
     */
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens != null ? new ArrayList<>(imagens) : new ArrayList<>();
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public CafeEspecial getCafeEspecial() {
        return cafeEspecial;
    }

    public void setCafeEspecial(CafeEspecial cafeEspecial) {
        if (cafeEspecial != null) {
            cafeEspecial.setProduto(this);
        }
        this.cafeEspecial = cafeEspecial;
    }

    public MetodoPreparo getMetodoPreparo() {
        return metodoPreparo;
    }

    public void setMetodoPreparo(MetodoPreparo metodoPreparo) {
        if (metodoPreparo != null) {
            metodoPreparo.setProduto(this);
        }
        this.metodoPreparo = metodoPreparo;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", avaliacao=" + avaliacao +
                ", ativo=" + ativo +
                // Evitar chamada recursiva
                ", cafeEspecial=" + (cafeEspecial != null ? cafeEspecial.getId() : null) +
                ", metodoPreparo=" + (metodoPreparo != null ? metodoPreparo.getId() : null) +
                '}';
    }
}
