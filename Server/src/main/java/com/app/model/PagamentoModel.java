package com.app.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidade que representa a tabela 'pagamentos' no banco de dados.
 * Esta classe armazena as informações relacionadas aos pagamentos
 * realizados na aplicação, como método de pagamento, valor total,
 * valor com desconto, e o status do pagamento.
 * 
 * A anotação @Data da biblioteca Lombok é usada para gerar
 * automaticamente getters, setters e outros métodos comuns.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Data
@Entity
@Table(name = "pagamentos")
public class PagamentoModel {

    /**
     * ID do pagamento. Este campo é a chave primária da tabela 'pagamentos',
     * com geração automática de valor incremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do método de pagamento. Referencia a tabela 'metodosDePagamentos',
     * e é obrigatório.
     */
    @ManyToOne
    @JoinColumn(name = "MetodoId", nullable = false)
    private MetodoPagamentoModel metodoPagamento;

    /**
     * Data e hora em que o pagamento foi realizado. Este campo é obrigatório.
     */
    @Column(name = "dataHora", nullable = false)
    private String dataHora;

    /**
     * Status do pagamento. Este campo é obrigatório e armazena um valor inteiro
     * que representa o estado atual do pagamento, utilizando um enum.
     * Os valores possíveis são:
     * 1 - Pago
     * 2 - Pendente
     * 3 - Cancelado
     */
    @Column(name = "statusPagamento", nullable = false)
    private Integer statusPagamento;

    /**
     * Valor total do pagamento, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório.
     */
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Valor total com desconto aplicado, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório.
     */
    @Column(name = "totalComDesconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalComDesconto;

    /**
     * ID do pedido associado ao pagamento. Referencia a tabela 'pedidos',
     * e é obrigatório.
     */
    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private PedidoModel pedido;

    /**
     * ID do cupom aplicado ao pagamento, se houver. Referencia a tabela 'cupons'.
     */
    @ManyToOne
    @JoinColumn(name = "cupomId")
    private CupomModel cupom;

    /**
     * Valor do desconto aplicado, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório e seu valor padrão é 0.
     */
    @Column(name = "desconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto;

    /**
     * ID da transação gerada no processamento do pagamento. Este campo é opcional
     * e armazena até 255 caracteres.
     */
    @Column(name = "transactionId", length = 255)
    private String transactionId;

    /**
     * Enum que representa o status do pagamento.
     * Os valores possíveis são:
     * - PAGO (1)
     * - PENDENTE (2)
     * - CANCELADO (3)
     */
    public enum StatusPagamento {
        PAGO(1), PENDENTE(2), CANCELADO(3);

        private final int code;

        StatusPagamento(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static StatusPagamento fromCode(int code) {
            for (StatusPagamento status : StatusPagamento.values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Status inválido: " + code);
        }
    }
}
