package com.app.DTO;

import com.app.model.PagamentoModel;
import com.app.model.PagCartaoModel;



public class PagamentoRequest {
  private Long id;
    private PagamentoModel pagamento; // Modelo do pagamento
    private PagCartaoModel pagCartao; // Modelo do pagamento com cartão
    private Long metodoPagamentoId; // ID do método de pagamento
    private String dataHora; // Data e hora do pagamento
    private Long status; // Status do pagamento
    private Double valorTotal; // Valor total do pagamento
    private Double desconto; // Valor do desconto aplicado
    private Long pedidoId; // ID do pedido associado
    private Long cupomId; // ID do cupom aplicado
    private String transaction; // ou o tipo adequado para este campo

    // Getters e Setters

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PagamentoModel getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoModel pagamento) {
        this.pagamento = pagamento;
    }

    public PagCartaoModel getPagCartao() {
        return pagCartao;
    }

    public void setPagCartao(PagCartaoModel pagCartao) {
        this.pagCartao = pagCartao;
    }

    public Long getMetodoPagamentoId() {
        return metodoPagamentoId;
    }

    public void setMetodoPagamentoId(Long metodoPagamentoId) {
        this.metodoPagamentoId = metodoPagamentoId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getCupomId() {
        return cupomId;
    }

    public void setCupomId(Long cupomId) {
        this.cupomId = cupomId;
    }
}
