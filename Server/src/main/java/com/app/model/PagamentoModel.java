package com.app.model;

/* 
* Importa a classe BigDecimal para manipulação de valores decimais
* import java.math.BigDecimal;
* 
* Importa as anotações para mapeamento objeto-relacional
* import jakarta.persistence.*;
* 
* Importa a anotação @Data do Lombok para geração automática de métodos
* import lombok.Data;
*/
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa a tabela 'pagamentos' no banco de dados.
 * Esta classe armazena as informações relacionadas a um pagamento,
 * incluindo o método de pagamento, data e hora do pagamento, status,
 * valores totais e quaisquer descontos aplicados.
 * 
 * A anotação @Data da biblioteca Lombok é usada para gerar
 * automaticamente getters, setters e outros métodos comuns.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
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
     * ID do método de pagamento. Este campo referencia a tabela 'metodosDePagamentos',
     * e é obrigatório.
     */
    @ManyToOne
@JoinColumn(name = "metodo_pagamento_id", nullable = false)
private MetodoPagamentoModel metodoPagamento;

    /**
     * Data e hora em que o pagamento foi realizado. Este campo é obrigatório.
     */
    @Column(name = "dataHora", nullable = false)
    private String dataHora;

    /**
     * Status do pagamento. Este campo é obrigatório e armazena um valor inteiro
     * que representa o estado atual do pagamento, utilizando um enum (1 a 3).
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
     * Valor total do pagamento com desconto, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório.
     */
    @Column(name = "totalComDesconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalComDesconto;

    /**
     * ID do pedido associado a este pagamento. Este campo referencia a tabela 'pedidos',
     * e é obrigatório.
     */
    @Column(name = "pedidoId", nullable = false)
    private Integer pedidoId;

    /**
     * ID do cupom aplicado ao pagamento. Este campo referencia a tabela 'cupons',
     * e é opcional.
     */
     @ManyToOne
     @JoinColumn(name = "cupom_id", nullable = true)
     private CupomModel cupom; 

    /**
     * Valor do desconto aplicado ao pagamento, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório e tem um valor padrão de 0.
     */
    @Column(name = "desconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto;

    /**
     * ID da transação relacionada a este pagamento. Este campo é opcional
     * e pode ser utilizado para rastrear pagamentos em sistemas externos.
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

        /**
         * Retorna o código numérico correspondente ao status.
         * 
         * @return Código do status.
         */
        public int getCode() {
            return code;
        }

        /**
         * Converte um código numérico em um enum StatusPagamento.
         * 
         * @param code Código do status.
         * @return Enum correspondente ao código.
         * @throws IllegalArgumentException Se o código não corresponder a nenhum status válido.
         */
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
