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
 * Bibliotecas utilizadas:
 * 
 * - java.math.BigDecimal: Classe que representa um número decimal com precisão
 *   fixa, útil para representar valores financeiros, como preços.
 * - jakarta.validation.constraints.NotNull: Anotação que valida se um campo
 *   não é nulo.
 * - jakarta.validation.constraints.Size: Anotação que valida o tamanho de uma
 *   string, permitindo definir limites para o número de caracteres.
 * - lombok.Data: Anotação que gera automaticamente métodos getter, setter,
 *   toString, equals e hashCode para a classe, reduzindo a boilerplate.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
public class ProdutoDTO {

    private Long id; // ID do produto

    @NotNull(message = "O nome do produto não pode ser nulo")
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    private String nome; // Nome do produto

    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    private String descricao; // Descrição do produto

    @NotNull(message = "O preço não pode ser nulo")
    private BigDecimal preco; // Preço do produto

    private String imagem; // URL da imagem do produto

    @NotNull(message = "A quantidade em estoque não pode ser nula")
    private int quantidadeEstoque; // Quantidade disponível em estoque

    private Integer avaliacao; // Avaliação do produto (opcional)

    // Você pode adicionar outros campos conforme necessário
}
