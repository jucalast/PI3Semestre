package com.app.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade Produto.
 * Este DTO é utilizado para transferir dados de produtos
 * entre a camada de apresentação e a camada de serviço.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
public class ProdutoDTO {

    private Long id;

    @NotNull(message = "O nome do produto não pode ser nulo")
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    private String nome;

    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    private BigDecimal preco;

    private String imagem;

    @NotNull(message = "A quantidade em estoque não pode ser nula")
    private int quantidadeEstoque;

    private Integer avaliacao;

    // Você pode adicionar outros campos conforme necessário
}
