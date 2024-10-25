package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * Representa a entidade 'PagCartao' que armazena informações sobre pagamentos feitos com cartão.
 * Esta classe é mapeada para a tabela 'pagCartao' no banco de dados e inclui campos que
 * armazenam detalhes do cartão, informações do titular e do pagamento associado.
 * 
 * A anotação @Entity indica que esta classe é uma entidade JPA.
 * A anotação @Table especifica o nome da tabela no banco de dados.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Entity
@Table(name = "pagCartao")
public class PagCartaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A bandeira do cartão é obrigatória.")
    @Size(max = 50, message = "A bandeira do cartão não pode ter mais de 50 caracteres.")
    private String bandeiraCartao;

    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter exatamente 16 dígitos.")
    private String numero; // Idealmente, criptografado ou tokenizado

    @NotBlank(message = "A validade do cartão é obrigatória.")
    @Size(min = 5, max = 5, message = "A validade deve estar no formato MM/AA.")
    private String validade; // Formato: MM/AA

    @NotBlank(message = "O nome do titular do cartão é obrigatório.")
    @Size(max = 100, message = "O nome do titular não pode ter mais de 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
    private String cpf; // Garantir segurança e validação

    @NotNull(message = "O ID do pagamento associado é obrigatório.")
    private Long pagamentoId; // ID do pagamento associado

    private String autorizacaoCod;

    @NotNull(message = "O número de parcelas é obrigatório.")
    private Integer parcelas;

    // Getters e Setters (omitidos para brevidade)
}
