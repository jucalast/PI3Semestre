package com.app.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagamentoCompletoDTO {
    private Integer metodoPagamentoId;
    private String dataHora;
    private String statusPagamento;
    private BigDecimal total;
    private BigDecimal totalComDesconto;
    private Integer pedidoId;
    private Integer cupomId;
    private BigDecimal desconto;
    private String transactionId;

    // Dados do cartão
    private Long pagamentoId;
    private String bandeiraCartao;
    private String numero; 
    private String validade;
    private String nome;
    private String cpf;
    private String autorizacaoCod;
    private Integer parcelas;
}

