package com.app.model.pagamento;

import java.math.BigDecimal;

public enum TaxaPagamento {
    CREDITO(new BigDecimal("0.02")),  // Supondo 2% de taxa para crédito
    DEBITO(new BigDecimal("0.01")),   // Supondo 1% de taxa para débito
    BOLETO(new BigDecimal("2.50")),   // Supondo uma taxa fixa de R$2,50 para boleto
    PIX(BigDecimal.ZERO);             // Sem taxa para PIX

    private final BigDecimal taxa;

    TaxaPagamento(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getTaxa() {
        return this.taxa;
    }
}
