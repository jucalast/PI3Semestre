package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
     * ID do pagamento com cartão.
     * <p>
     * Este campo é a chave primária da tabela 'pagCartao', gerado automaticamente
     * com uma estratégia de incremento automático (IDENTITY).
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pagamento associado a este registro de pagamento com cartão.
     * <p>
     * Este campo estabelece uma associação ManyToOne com a entidade PagamentoModel,
     * e é obrigatório (nullable = false) para garantir que cada registro de
     * pagamento com cartão esteja vinculado a um pagamento específico.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "pagamentoId", nullable = false)
    private PagamentoModel pagamentoId;

    /**
     * Bandeira do cartão utilizado no pagamento.
     * <p>
     * Este campo é uma cadeia de caracteres com um limite de 50 caracteres.
     * É obrigatório, e representa a bandeira do cartão (exemplo: VISA, MasterCard).
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(50)")
    @NotNull(message = "A bandeira do cartão é obrigatória.")
    private String bandeiraCartao;

    /**
     * Número do cartão utilizado no pagamento.
     * <p>
     * Este campo deve conter exatamente 16 dígitos e representa o número completo do cartão.
     * É obrigatório e deve seguir o formato de 16 dígitos para cartões padrão.
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(16)")
    @NotNull(message = "O número do cartão é obrigatório.")
    private String numero;

    /**
     * Data de validade do cartão no formato MM/AA.
     * <p>
     * Este campo deve seguir o formato de cinco caracteres (MM/AA) e é obrigatório.
     * Representa a validade do cartão.
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(5)")
    @NotNull(message = "A data de validade é obrigatória.")
    private String validade;

    /**
     * Nome do titular do cartão.
     * <p>
     * Este campo representa o nome completo do titular do cartão e possui um limite
     * de 100 caracteres. É obrigatório.
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(100)")
    @NotNull(message = "O nome do titular é obrigatório.")
    private String nome;

    /**
     * CPF do titular do cartão.
     * <p>
     * Este campo representa o CPF do titular do cartão, contendo exatamente 11 dígitos.
     * É obrigatório e permite garantir a identidade do titular do cartão.
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(11)")
    @NotNull(message = "O CPF do titular é obrigatório.")
    private String cpf;

    /**
     * Código de autorização da transação.
     * <p>
     * Este campo é opcional e armazena o código de autorização gerado pela instituição financeira
     * para validar a transação. Ele pode ter até 50 caracteres.
     * </p>
     */
    @Column(columnDefinition = "VARCHAR(50)")
    private String autorizacaoCod;

    /**
     * Número de parcelas do pagamento.
     * <p>
     * Este campo é obrigatório e representa o número de parcelas escolhidas para
     * dividir o pagamento.
     * </p>
     */
    @NotNull(message = "O número de parcelas é obrigatório.")
    private Integer parcelas;

    // Getters e Setters (omitidos para brevidade)
}
