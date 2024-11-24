package com.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa os detalhes de pagamento para um pedido específico.
 */
@Data
@Entity
@Table(name = "pagamentos")
public class PagamentoModel {

    /**
     * Código de transação único, gerado automaticamente.
     */
    @Id
    @Column(name = "codigo_transacao", nullable = false, updatable = false, unique = true, length = 100)
    private String codigoTransacao = UUID.randomUUID().toString();

    /**
     * Valor total pago ou a ser pago.
     */
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    /**
     * Data e hora do pagamento.
     */
    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    /**
     * Método de pagamento utilizado (e.g., 'Cartão de Crédito', 'Boleto').
     */
    @Column(name = "metodo_pagamento", nullable = false, length = 50)
    private String metodoPagamento;

    /**
     * Status atual do pagamento (e.g., 'Aprovado', 'Pendente').
     */
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    /**
     * Pedido associado a este pagamento.
     */
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoModel pedido;

    /**
     * Cartão utilizado para o pagamento, se aplicável.
     */
    @ManyToOne
    @JoinColumn(name = "cartao_id", referencedColumnName = "numero_cartao")
    private CartaoModel cartao;

    /**
     * Desconto aplicado ao pagamento.
     */
    @Column(name = "desconto", precision = 10, scale = 2)
    private BigDecimal desconto;

    /**
     * Taxas adicionais aplicadas ao pagamento.
     */
    @Column(name = "taxa", precision = 10, scale = 2)
    private BigDecimal taxa;
}
