package com.app.DTO;

import lombok.Data;

/**
 * Classe de Transferência de Dados (DTO) que representa um pagamento completo
 * que inclui informações tanto do pagamento em si quanto dos detalhes do pagamento
 * realizado com cartão.
 * 
 * Utiliza a biblioteca Lombok para geração automática de métodos getter e setter.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 * 
 * @see PagamentoCompletoDTO
 * @see PagCartaoDTO
 */
@Data
public class PagamentoCompletoComCartaoDTO {
    
    /** 
     * Objeto que contém as informações do pagamento completo.
     */
    private PagamentoCompletoDTO pagamentoDTO;

    /** 
     * Objeto que contém as informações específicas do pagamento com cartão.
     */
    private PagCartaoDTO pagCartaoDTO;

    // Getters e Setters são gerados automaticamente pelo Lombok através da anotação @Data.
}
