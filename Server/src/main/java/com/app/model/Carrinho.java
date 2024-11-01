package com.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

/**
 * Representa a entidade Carrinho.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carrinho {

    /**
     * Identificador único do carrinho.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Carrinho")
    private Long id;

    /**
     * Valor do frete associado ao carrinho.
     * Precision: 10 dígitos no total, incluindo 2 casas decimais.
     */
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal valorFrete;
}
