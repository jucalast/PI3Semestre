package com.app.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamentos")
public class PagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "metodoPagamentoId", nullable = false)
    private MetodoPagamentoModel metodoPagamentoId;

    @Column(name = "dataHora", nullable = false)
    private String dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusPagamento", nullable = false)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "totalComDesconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalComDesconto;
    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private PedidoModel pedidoId;

    @ManyToOne
    @JoinColumn(name = "cupomId", nullable = false)
    private CupomModel cupomId;

    @Column(name = "desconto", precision = 10, scale = 2, nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "transactionId", length = 255)
    private String transactionId;

    private PagCartaoModel pagCartaoModel;

    public enum StatusPagamento {
        PAGO(1),
        PENDENTE(2),
        CANCELADO(3);

        private final int code;

        StatusPagamento(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static StatusPagamento fromCode(int code) {
            switch (code) {
                case 1: return PAGO;
                case 2: return PENDENTE;
                case 3: return CANCELADO;
                default: throw new IllegalArgumentException("Status inválido: " + code);
            }
        }
    }
}
