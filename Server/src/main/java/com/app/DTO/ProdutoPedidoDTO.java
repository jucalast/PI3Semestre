package com.app.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProdutoPedidoDTO {
    private Long produtoId; // ID do produto
    private Integer quantidade; // Quantidade do produto
    private BigDecimal subtotal; // Subtotal do produto
}
