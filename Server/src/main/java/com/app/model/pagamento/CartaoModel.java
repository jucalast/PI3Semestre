package com.app.model.pagamento;

import com.app.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Modelo representativo da tabela de cartões no banco de dados.
 * Armazena informações detalhadas sobre cartões de crédito dos usuários.
 */
@Data
@Entity
@Table(name = "cartoes")
public class CartaoModel {

    /**
     * Número do cartão, usado como identificador único.
     * Deve ser tratado com alta confidencialidade.
     */
    @Id
    @Column(name = "numero_cartao", nullable = false, length = 16)
    private String numeroCartao;

    /**
     * Nome do titular do cartão como registrado no banco emissor.
     */
    @Column(name = "nome_titular", nullable = false, length = 100)
    private String nomeTitular;

    /**
     * Data de validade do cartão no formato MM/YYYY.
     */
    @Column(name = "validade", nullable = false, length = 7)
    private String validade;

    /**
     * Bandeira do cartão (Visa, MasterCard, etc.).
     */
    @Column(name = "bandeira", nullable = false, length = 50)
    private String bandeira;

    /**
     * CPF do titular do cartão.
     */
    @Column(name = "cpf_titular", nullable = false, length = 11)
    private String cpfTitular;

    /**
     * Usuário associado a este cartão.
     * Relacionamento inverso da chave estrangeira na tabela de usuários.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserModel user;

    /**
     * Método auxiliar para expor o ID do usuário associado ao cartão através de JSON.
     * @return O ID do usuário, se houver; caso contrário, null.
     */
    @JsonProperty("userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
