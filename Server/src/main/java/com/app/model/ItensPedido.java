package com.app.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * A classe ItensPedido mapeia a tabela "itensPedido" no banco de dados.
 * @Entity indica que a classe é uma entidade JPA.
 * @Table(name = "itensPedido") especifica o nome da tabela no banco de dados.
 */
@Entity
@Table(name = "itensPedido")
public class ItensPedido {

    /**
     * Relacionamento muitos-para-um com a entidade Pedido.
     * @ManyToOne indica que vários itens podem estar associados a um único pedido.
     * @JoinColumn(name = "pedidoId", nullable = false) define a chave estrangeira "pedidoId" na tabela "itensPedido".
     */
    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private Pedido pedido;

    /**
     * Relacionamento muitos-para-um com a entidade Produto.
     * @ManyToOne indica que vários itens podem estar associados a um único produto.
     * @JoinColumn(name = "produtoId", nullable = false) define a chave estrangeira "produtoId" na tabela "itensPedido".
     */
    @ManyToOne
    @JoinColumn(name = "produtoId", nullable = false)
    private Produto produto;

    /**
     * Coluna que armazena a quantidade do produto.
     * @Column(name = "quantidade", nullable = false) define que a coluna "quantidade" não pode ser nula.
     */
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    /**
     * Coluna que armazena o subtotal do item.
     * @Column(name = "subtotal", nullable = false) define que a coluna "subtotal" não pode ser nula.
     * BigDecimal é usado para garantir precisão em cálculos monetários.
     */
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    // Getters e Setters

    /**
     * Retorna o pedido associado ao item.
     * @return pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Define o pedido associado ao item.
     * @param pedido
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Retorna o produto associado ao item.
     * @return produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado ao item.
     * @param produto
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a quantidade do produto.
     * @return quantidade
     */
    public Integer getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto.
     * @param quantidade
     */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o subtotal do item.
     * @return subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * Define o subtotal do item.
     * @param subtotal
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
