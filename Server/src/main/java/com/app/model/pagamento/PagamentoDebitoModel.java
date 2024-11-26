package com.app.model.pagamento;


import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Representa um pagamento específico realizado via cartão de débito.
 */
@Entity
@Table(name = "pagamentos_debito")
@DiscriminatorValue("DEBITO")
public class PagamentoDebitoModel extends MetodoPagamentoModel {

    @OneToOne
    @JoinColumn(name = "cartao_id", referencedColumnName = "numero_cartao")
    private CartaoModel cartao;

    @Column(name = "taxa_transacao", precision = 5, scale = 2)
    private BigDecimal taxaTransacao;

    // Construtor
    public PagamentoDebitoModel() {
        this.setTipoPagamento(TipoPagamento.DEBITO);
        this.taxaTransacao = TaxaPagamento.DEBITO.getTaxa();
    }

    public void setCartao(CartaoModel cartao) {
        this.cartao = cartao;
    }

    public void setTaxaTransacao(BigDecimal taxaTransacao) {
        this.taxaTransacao = taxaTransacao;
    }



}