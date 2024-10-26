package com.app.DTO;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade ProdutoPedido.
 * Este DTO é utilizado para transferir dados relacionados a produtos
 * em um pedido entre a camada de apresentação e a camada de serviço.
 * 
 * Bibliotecas utilizadas:
 * 
 * - java.math.BigDecimal: Classe que representa um número decimal com precisão
 *   fixa, útil para representar valores financeiros, como subtotais de produtos.
 * - lombok.Data: Anotação que gera automaticamente métodos getter, setter,
 *   toString, equals e hashCode para a classe, reduzindo a boilerplate.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
public class ProdutoPedidoDTO {
    
    private Long produtoId; // ID do produto
    private Integer quantidade; // Quantidade do produto
    private BigDecimal subtotal; // Subtotal do produto
}
