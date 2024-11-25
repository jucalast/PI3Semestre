package com.app.model.pagamento;

import com.app.model.PedidoModel;
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

    @Id
    @Column(name = "codigo_transacao", nullable = false, updatable = false, unique = true, length = 100)
    private String codigoTransacao = UUID.randomUUID().toString();

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metodo_pagamento_id", referencedColumnName = "id") // Agora aponta para o ID
    private MetodoPagamentoModel metodoPagamento;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoModel pedido;

    @Column(name = "desconto", precision = 10, scale = 2)
    private BigDecimal desconto;
}
