package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Modelo da entidade Avaliacao.
 * Representa uma avaliação realizada por um usuário para um produto em um pedido.
 *
 * <p>Contém informações sobre o usuário, ProdutoPedido associado, nota e uma descrição opcional.
 *
 * @since 1.0
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avaliacao")
public class AvaliacaoModel {

    /**
     * ID da avaliação, chave primária da entidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Usuário que realizou a avaliação.
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserModel user;

    /**
     * ProdutoPedido associado à avaliação.
     */
    @ManyToOne
    @JoinColumn(name = "produtoPedidoId", nullable = false)
    private ProdutoPedidoModel produtoPedido;

    /**
     * Nota atribuída pelo usuário ao ProdutoPedido.
     */
    @Column(nullable = false)
    private int nota;

    /**
     * Descrição opcional fornecida pelo usuário sobre a avaliação.
     */
    @Column(length = 1024)
    private String descricao;
}
