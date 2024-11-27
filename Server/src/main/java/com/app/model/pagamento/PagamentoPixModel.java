package com.app.model.pagamento;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Representa um pagamento específico realizado via Pix.
 */
@Entity
@Table(name = "pagamentos_pix")
@DiscriminatorValue("PIX")
public class PagamentoPixModel extends MetodoPagamentoModel {

    /**
     * Chave Pix utilizada para a transação.
     */
    @Column(name = "chave_pix", nullable = false, length = 100)
    private String chavePix;

    /**
     * Taxa aplicada ao pagamento via Pix, se houver.
     */
    @Column(name = "taxa_pix", precision = 5, scale = 2)
    private BigDecimal taxaPix = BigDecimal.ZERO; // Inicializado como ZERO, pode ser alterado se necessário

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public void setTaxaPix(BigDecimal taxaPix) {
        this.taxaPix = taxaPix;
    }

}