package com.app.model.pagamento;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa um pagamento específico realizado via boleto bancário.
 */
@Entity
@Table(name = "pagamentos_boleto")
@DiscriminatorValue("BOLETO")
public class PagamentoBoletoModel extends MetodoPagamentoModel {

    /**
     * Código de barras do boleto.
     */
    @Column(name = "codigo_boleto", nullable = false, length = 48)
    private String codigoBoleto;

    /**
     * Data de vencimento do boleto.
     */
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    /**
     * Taxa de processamento aplicada ao uso do boleto, se houver.
     */
    @Column(name = "taxa_boleto", precision = 5, scale = 2)
    private BigDecimal taxaBoleto;

    // Construtor
    public PagamentoBoletoModel() {
        this.setTipoPagamento(TipoPagamento.BOLETO);
        this.taxaBoleto = TaxaPagamento.BOLETO.getTaxa();
    }

    public void setCodigoBoleto(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setTaxaBoleto(BigDecimal taxaBoleto) {
        this.taxaBoleto = taxaBoleto;
    }

}
