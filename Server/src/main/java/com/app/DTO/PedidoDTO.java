package com.app.DTO;

import java.math.BigDecimal;
import java.util.List;


import lombok.Data;

/**
 * Classe Data Transfer Object (DTO) para representar um pedido na aplicação.
 * 
 * Esta classe é utilizada para transferir dados relacionados a um pedido
 * entre diferentes camadas da aplicação, facilitando a manipulação e
 * a troca de informações sobre pedidos de forma simplificada.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
public class PedidoDTO {
    
    private Integer usuarioId;            // ID do usuário que fez o pedido
    private String dataPedido;             // Data em que o pedido foi realizado
    private Integer statusPedido;          // Status atual do pedido (ex: 0 - Pendente, 1 - Confirmado, etc.)
    private BigDecimal total;              // Total do pedido
    private String observacoes;            // Observações adicionais sobre o pedido
    private String estado;                 // Estado de entrega do pedido
    private String cep;                    // Código Postal para entrega
    private String cidade;                 // Cidade para entrega
    private String bairro;                 // Bairro para entrega
    private String complemento;            // Complemento para entrega (ex: apartamento, bloco, etc.)
    private Integer numero;                // Número do endereço para entrega
    private String rua;                    // Rua do endereço para entrega
    private List<ProdutoPedidoDTO> produtos; // Lista de produtos do pedido
}
