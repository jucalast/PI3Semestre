package com.app.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * A classe MetodoDePagamento mapeia a tabela "metodosDePagamentos" no banco de dados.
 * @Entity indica que a classe é uma entidade JPA.
 * @Table(name = "metodosDePagamentos") especifica o nome da tabela no banco de dados.
 */
@Entity
@Table(name = "metodosDePagamentos")
public class MetodosDePagamentos {

    /**
     * Campo identificador da entidade.
     * @Id indica que o campo é uma chave primária.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) define que o valor do ID será gerado automaticamente pelo banco de dados.
     * @Column(name = "id", updatable = false, nullable = false) define que a coluna "id" não pode ser nula e não pode ser atualizada.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    /**
     * Coluna que armazena o nome do método de pagamento.
     * @Column(name = "nome", nullable = false) define que a coluna "nome" não pode ser nula.
     * Enumerated(EnumType.STRING) é usada para mapear a coluna como uma string, representando os valores permitidos: 'Boleto', 'Credito', 'Pix', 'Debito'.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private MetodoPagamentoEnum nome;

    /**
     * Coluna que armazena a taxa associada ao método de pagamento.
     * @Column(name = "taxa", nullable = false) define que a coluna "taxa" não pode ser nula.
     * BigDecimal é usado para garantir precisão em cálculos monetários.
     */
    @Column(name = "taxa", nullable = false)
    private BigDecimal taxa;

    // Getters e Setters

    /**
     * Retorna o ID do método de pagamento.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do método de pagamento.
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do método de pagamento.
     * @return nome
     */
    public MetodoPagamentoEnum getNome() {
        return nome;
    }

    /**
     * Define o nome do método de pagamento.
     * @param nome
     */
    public void setNome(MetodoPagamentoEnum nome) {
        this.nome = nome;
    }

    /**
     * Retorna a taxa associada ao método de pagamento.
     * @return taxa
     */
    public BigDecimal getTaxa() {
        return taxa;
    }

    /**
     * Define a taxa associada ao método de pagamento.
     * @param taxa
     */
    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    /**
     * Enum para representar os valores permitidos no campo "nome".
     */
    public enum MetodoPagamentoEnum {
        BOLETO("Boleto"),
        CREDITO("Credito"),
        PIX("Pix"),
        DEBITO("Debito");

        private final String value;

        MetodoPagamentoEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
