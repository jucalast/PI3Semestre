package com.app.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {
    private Integer usuarioId;
    private String dataPedido;
    private Integer statusPedido;
    private BigDecimal total;
    private String observacoes;
    private String estado;
    private String cep;
    private String cidade;
    private String bairro;
    private String complemento;
    private Integer numero;
    private String rua;
    private List<ProdutoPedidoDTO> produtos; // Lista de produtos do pedido
}
