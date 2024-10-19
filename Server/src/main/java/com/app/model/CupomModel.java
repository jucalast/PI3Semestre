package com.app.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Classe que define o modelo da entidade 'CupomModel' no banco de dados.
 * 
 * <p>Representa a estrutura da tabela 'cupons' com campos para id, nome, desconto,
 * descrição e data de validade. Cada campo é mapeado para uma coluna correspondente na
 * tabela do banco de dados.</p>
 * 
 * <ul>
 *   <li>**id**: Chave primária da tabela, gerada automaticamente.</li>
 *   <li>**nome**: Nome do cupom, que não pode ser nulo, com no máximo 100 caracteres.</li>
 *   <li>**desconto**: Percentual de desconto oferecido pelo cupom, que não pode ser nulo.</li>
 *   <li>**descricao**: Descrição do cupom, que não pode ser nula, com no máximo 100 caracteres.</li>
 *   <li>**dataValidade**: Data de validade do cupom, que não pode ser nula.</li>
 * </ul>
 * 
 * <p>O uso da anotação `@Entity` indica que esta classe é uma entidade JPA, e a anotação
 * `@Table` define o nome da tabela correspondente no banco de dados.</p>
 * 
 * <p>Validações são realizadas por meio das anotações `@NotNull`, para garantir que
 * os campos obrigatórios sejam preenchidos corretamente.</p>
 * 
 * <p>Esta classe também fornece métodos getters e setters para acessar e modificar os valores dos
 * campos.</p>
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Entity
@Table(name = "cupons")
public class CupomModel {

    /**
     * Identificador único para cada cupom. Este valor é gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nome do cupom. Este campo não pode ser nulo e tem no máximo 100 caracteres.
     */
    @NotNull(message = "O nome do cupom não pode ser nulo")
    @Column(nullable = false, length = 100)
    private String nome;

    /**
     * Percentual de desconto oferecido pelo cupom. Este campo não pode ser nulo.
     */
    @NotNull(message = "O desconto não pode ser nulo")
    @Column(nullable = false)
    private Float desconto;

    /**
     * Descrição do cupom. Este campo não pode ser nulo e tem no máximo 100 caracteres.
     */
    @NotNull(message = "A descrição não pode ser nula")
    @Column(nullable = false, length = 100)
    private String descricao;

    /**
     * Data de validade do cupom. Este campo não pode ser nulo.
     */
    @NotNull(message = "A data de validade não pode ser nula")
    @Column(nullable = false)
    private String dataValidade;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getDesconto() {
        return desconto;
    }

    public void setDesconto(Float desconto) {
        this.desconto = desconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
}
