package com.app.DTO;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Classe de Transferência de Dados (DTO) que representa as informações de um
 * pagamento completo, incluindo detalhes do método de pagamento e informações
 * adicionais relevantes ao pagamento.
 * 
 * Utiliza a biblioteca Lombok para geração automática de métodos getter e setter.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 * 
 * @see PagCartaoDTO
 */
@Data
public class PagamentoCompletoDTO {
    
    /** 
     * ID do método de pagamento utilizado.
     */
    private Integer metodoPagamentoId;

    /** 
     * Data e hora em que o pagamento foi realizado.
     */
    private String dataHora;

    /** 
     * Status do pagamento (ex: PENDENTE, COMPLETO, CANCELADO).
     */
    private String statusPagamento;

    /** 
     * Valor total do pagamento.
     */
    private BigDecimal total;

    /** 
     * Valor total do pagamento após a aplicação de descontos.
     */
    private BigDecimal totalComDesconto;

    /** 
     * ID do pedido associado ao pagamento.
     */
    private Integer pedidoId;

    /** 
     * ID do cupom utilizado no pagamento, se houver.
     */
    private Integer cupomId;

    /** 
     * Valor do desconto aplicado ao pagamento.
     */
    private BigDecimal desconto;

    /** 
     * ID da transação gerada pelo sistema de pagamento.
     */
    private String transactionId;

    /** 
     * Objeto que contém as informações específicas do pagamento realizado com cartão.
     */
    private PagCartaoDTO pagCartaoDTO;

    // Getters e Setters são gerados automaticamente pelo Lombok através da anotação @Data.
}
