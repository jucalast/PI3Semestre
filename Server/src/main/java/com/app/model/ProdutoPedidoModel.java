package com.app.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Entidade que representa a tabela 'produtoPedido' no banco de dados.
 * Esta classe armazena as informações relacionadas aos produtos de um pedido,
 * incluindo a quantidade e o subtotal.
 * 
 * A anotação @Data da biblioteca Lombok é usada para gerar
 * automaticamente getters, setters e outros métodos comuns.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Entity
@Table(name = "produtoPedido")
public class ProdutoPedidoModel {

    /**
     * ID da tabela produtoPedido. Este campo é a chave primária com auto incremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ID do pedido. Este campo referencia a tabela 'pedidos'.
     * Este campo é obrigatório.
     */
    @NotNull(message = "O ID do pedido não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private PedidoModel pedido;

    /**
     * ID do produto. Este campo referencia a tabela 'produtos'.
     * Este campo é obrigatório.
     */
    @NotNull(message = "O ID do produto não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "produtosId", nullable = false)
    private Produto produto;

    /**
     * Quantidade do produto no pedido. Este campo é obrigatório.
     */
    @NotNull(message = "A quantidade não pode ser nula")
    @Column(nullable = false)
    private Integer quantidade;

    /**
     * Subtotal do produto no pedido. Este campo é obrigatório.
     */
    @NotNull(message = "O subtotal não pode ser nulo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}
