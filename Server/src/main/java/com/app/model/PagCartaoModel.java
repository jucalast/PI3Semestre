package com.app.model;

/* 
* Importa as anotações para mapeamento objeto-relacional
* import jakarta.persistence.*;
* 
* Importa a anotação @Data do Lombok para geração automática de métodos
* import lombok.Data;
*/
import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa a tabela 'pagCartao' no banco de dados.
 * Esta classe armazena as informações relacionadas a pagamentos realizados
 * com cartão de crédito, incluindo detalhes do cartão, dados do titular,
 * e informações de pagamento.
 * 
 * A anotação @Data da biblioteca Lombok é usada para gerar
 * automaticamente getters, setters e outros métodos comuns.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
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
     * Bandeira do cartão (ex: Visa, MasterCard). Este campo é obrigatório e
     * contém até 50 caracteres.
     */
    @Column(name = "bandeiraCartao", nullable = false, length = 50)
    private String bandeiraCartao;

    /**
     * Número do cartão de crédito. Este campo é obrigatório e contém até 16 caracteres.
     * É idealmente criptografado ou tokenizado para garantir a segurança dos dados.
     */
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;

    /**
     * Data de validade do cartão. Este campo é obrigatório e deve estar no formato MM/AA.
     */
    @Column(name = "validade", nullable = false, length = 5)
    private String validade;

    /**
     * Nome do titular do cartão. Este campo é obrigatório e contém até 100 caracteres.
     */
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /**
     * CPF do titular do cartão. Este campo é obrigatório e contém 11 caracteres.
     * É importante garantir a segurança e validação dos dados.
     */
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    /**
     * ID do pagamento associado a este cartão. Este campo referencia a tabela 'pagamentos',
     * e é obrigatório.
     */
    @Column(name = "pagamentoId", nullable = false)
    private Integer pagamentoId;

    /**
     * Código de autorização do pagamento. Este campo é opcional e pode conter até 100 caracteres.
     */
    @Column(name = "autorizacaoCod", length = 100)
    private String autorizacaoCod;

    /**
     * Número de parcelas do pagamento. Este campo é obrigatório e armazena um valor inteiro
     * que representa a quantidade de parcelas em que o pagamento foi realizado.
     */
    @Column(name = "parcelas", nullable = false)
    private Integer parcelas;
}
