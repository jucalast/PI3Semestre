package com.app.DTO;

import lombok.Data;

/**
 * Classe de Transferência de Dados (DTO) que representa as informações
 * de um pagamento realizado com cartão, incluindo detalhes do cartão e
 * informações do pagamento associado.
 * 
 * Utiliza a biblioteca Lombok para a geração automática de métodos getter e setter.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Data
public class PagCartaoDTO {
    
    /** 
     * ID do pagamento associado a este cartão.
     */
    private Long pagamentoId;

    /** 
     * Bandeira do cartão (ex: Visa, MasterCard, etc.).
     */
    private String bandeiraCartao;

    /** 
     * Número do cartão (normalmente mascarado para segurança).
     */
    private String numero;

    /** 
     * Data de validade do cartão (formato: MM/AAAA).
     */
    private String validade;

    /** 
     * Nome do titular do cartão.
     */
    private String nome;

    /** 
     * CPF do titular do cartão.
     */
    private String cpf;

    /** 
     * Código de autorização da transação, retornado pela instituição financeira.
     */
    private String autorizacaoCod;

    /** 
     * Número de parcelas em que o pagamento foi dividido.
     */
    private Integer parcelas;

    // Getters e Setters são gerados automaticamente pelo Lombok através da anotação @Data.
}
