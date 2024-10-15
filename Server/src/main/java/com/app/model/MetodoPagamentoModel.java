package com.app.model;

/**
 * Bibliotecas utilizadas e suas funções:
 * - jakarta.persistence.Column: define uma coluna da tabela no banco de dados.
 * - jakarta.persistence.Entity: marca a classe como uma entidade JPA.
 * - jakarta.persistence.GeneratedValue: especifica que o valor do campo será gerado automaticamente.
 * - jakarta.persistence.GenerationType: define a estratégia de geração automática de valores (ex.: IDENTITY).
 * - jakarta.persistence.Id: marca o campo como a chave primária da tabela.
 * - jakarta.persistence.Table: define o nome da tabela no banco de dados para esta entidade.
 * - jakarta.validation.constraints.NotNull: valida que o campo não pode ser nulo.
 * - jakarta.validation.constraints.Pattern: valida que o campo deve seguir um padrão específico (ex.: regex).
 * - lombok.Data: gera automaticamente os métodos getters, setters, equals, hashCode e toString.
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;




/**
 * Classe que representa um método de pagamento.
 * Esta classe está mapeada para a tabela 'metodosDePagamentos' no banco de dados.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-14
 */
@Data
@Entity
@Table(name = "metodosDePagamentos")
public class MetodoPagamentoModel {

    /**
     * Atributo id.
     * Chave primária da tabela.
     * Gerada automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Atributo nome.
     * Representa o tipo de método de pagamento (Boleto, Crédito, Pix, Débito).
     * O valor deve ser um dos quatro tipos especificados.
     * Não pode ser nulo.
     */
    @NotNull(message = "O nome do método de pagamento não pode ser nulo")
    @Pattern(regexp = "Boleto|Credito|Pix|Debito", message = "O nome do método de pagamento deve ser Boleto, Credito, Pix ou Debito")
    @Column(nullable = false)
    private String nome;

    /**
     * Atributo taxa.
     * Representa a taxa aplicada pela instituição financeira em cada transação.
     * Não pode ser nula.
     */
    @NotNull(message = "A taxa não pode ser nula")
    @Column(nullable = false)
    private Float taxa;

    /**
     * Atributo empresa.
     * Nome da empresa ou instituição financeira que processa o pagamento.
     * Pode ter até 100 caracteres.
     */
    @Column(length = 100)
    private String empresa;
}
