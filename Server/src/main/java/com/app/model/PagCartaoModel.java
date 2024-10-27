package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

/**
 * Representa a entidade 'PagCartao' que armazena informações sobre pagamentos feitos com cartão.
 * Esta classe é mapeada para a tabela 'pagCartao' no banco de dados e inclui campos que
 * armazenam detalhes do cartão, informações do titular e do pagamento associado.
 * 
 * A anotação @Entity indica que esta classe é uma entidade JPA.
 * A anotação @Table especifica o nome da tabela no banco de dados.
 * 
 * A anotação @Data da biblioteca Lombok é utilizada para gerar automaticamente 
 * métodos comuns como getters, setters, toString, equals e hashCode.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
*/
@Data 
@Entity
@Table(name = "pagCartao")
public class PagCartaoModel {

    /**
     * ID do pagamento com cartão. Este campo é a chave primária da tabela 'pagCartao',
     * com geração automática de valor incremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pagamento associado a este registro de pagamento com cartão. Este campo é uma referência
     * para a entidade PagamentoModel, obrigatória para garantir a associação correta
     * entre os pagamentos e suas informações de cartão.
     */
    @NotNull(message = "O pagamento associado é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "pagamentoId")
    private PagamentoModel pagamentoId;

    /**
     * Bandeira do cartão utilizado para o pagamento. Este campo é obrigatório e deve
     * ter no máximo 50 caracteres.
     */
    @NotBlank(message = "A bandeira do cartão é obrigatória.")
    @Size(max = 50, message = "A bandeira do cartão não pode ter mais de 50 caracteres.")
    private String bandeiraCartao;

    /**
     * Número do cartão utilizado para o pagamento. Este campo é obrigatório e deve
     * conter exatamente 16 dígitos. É recomendado que o número do cartão seja 
     * criptografado ou tokenizado por questões de segurança.
     */
    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter exatamente 16 dígitos.")
    private String numero; // Idealmente, criptografado ou tokenizado

    /**
     * Data de validade do cartão, que é obrigatória e deve estar no formato MM/AA.
     */
    @NotBlank(message = "A validade do cartão é obrigatória.")
    @Size(min = 5, max = 5, message = "A validade deve estar no formato MM/AA.")
    private String validade; // Formato: MM/AA

    /**
     * Nome do titular do cartão. Este campo é obrigatório e deve ter no máximo 100 caracteres.
     */
    @NotBlank(message = "O nome do titular do cartão é obrigatório.")
    @Size(max = 100, message = "O nome do titular não pode ter mais de 100 caracteres.")
    private String nome;

    /**
     * CPF do titular do cartão. Este campo é obrigatório e deve conter exatamente 11 dígitos.
     * É importante garantir a segurança e a validação do CPF.
     */
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
    private String cpf; // Garantir segurança e validação

    /**
     * Código de autorização do pagamento. Este campo é opcional e pode ser usado para armazenar
     * informações adicionais relacionadas à autorização do pagamento.
     */
    private String autorizacaoCod;

    /**
     * Número de parcelas em que o pagamento foi realizado. Este campo é obrigatório.
     */
    @NotNull(message = "O número de parcelas é obrigatório.")
    private Integer parcelas;

    // Getters e Setters (omitidos para brevidade)
}
