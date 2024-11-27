package com.app.model.pagamento;


import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Representa um pagamento específico realizado via cartão de débito.
 */
@Entity
@Table(name = "pagamentos_debito", indexes = {@Index(name = "IDX_CARTAO_ID", columnList = "cartao_id", unique = false)})
@DiscriminatorValue("DEBITO")
public class PagamentoDebitoModel extends MetodoPagamentoModel {

    @ManyToOne
    @JoinColumn(name = "cartao_id", referencedColumnName = "numero_cartao")
    private CartaoModel cartao;

    @Column(name = "taxa_transacao", precision = 5, scale = 2)
    private BigDecimal taxaTransacao;


    public void setCartao(CartaoModel cartao) {
        this.cartao = cartao;
    }

    public void setTaxaTransacao(BigDecimal taxaTransacao) {
        this.taxaTransacao = taxaTransacao;
    }


    public PagamentoDebitoModel() {
        super();
        this.setTipoPagamento(TipoPagamento.DEBITO);
        this.taxaTransacao = TaxaPagamento.DEBITO.getTaxa();
        this.setStatus("Aprovado"); // Garantir que isso esteja sendo feito
    }

}