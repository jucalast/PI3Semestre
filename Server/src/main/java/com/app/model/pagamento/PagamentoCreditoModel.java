package com.app.model.pagamento;


import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Representa um pagamento específico realizado via cartão de crédito.
 */
@Entity
@Table(name = "pagamentos_credito")
@DiscriminatorValue("CREDITO")
public class PagamentoCreditoModel extends MetodoPagamentoModel {

    @ManyToOne
    @JoinColumn(name = "cartao_id", referencedColumnName = "numero_cartao")
    private CartaoModel cartao;

    @Column(name = "numero_parcelas", nullable = false)
    private int numeroParcelas;

    @Column(name = "taxa_juros", precision = 5, scale = 2)
    private BigDecimal taxaJuros;

    // Construtor
    public PagamentoCreditoModel() {
        this.setTipoPagamento(TipoPagamento.CREDITO);
        this.taxaJuros = TaxaPagamento.CREDITO.getTaxa();
    }

    public void setCartao(CartaoModel cartao) {
        this.cartao = cartao;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }


}