package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa a entidade Usuário para a aplicação.
 * Esta classe está mapeada para a tabela 'TB_USER' no banco de dados.
 *
 * A classe utiliza anotações do Lombok para gerar automaticamente
 * código boilerplate como getters, setters, construtores e o método toString.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class UserModel {

    /**
     * Identificador único do usuário.
     * Este campo é gerado automaticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Nome do usuário.
     * Não pode ser nulo.
     */
    @Column(nullable = false, name = "USER_NAME")
    private String userName;

    /**
     * Endereço de e-mail do usuário.
     * Não pode ser nulo e deve ser único no banco de dados.
     */
    @Column(nullable = false, name = "EMAIL_ID", unique = true)
    private String emailId;

    /**
     * Número de telefone do usuário.
     * Este campo é opcional.
     */
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    /**
     * Funções atribuídas ao usuário.
     * Não pode ser nulo.
     */
    @Column(nullable = false, name = "ROLES")
    private String roles;

    /**
     * Construtor personalizado com nome, e-mail e funções.
     *
     * @param userName O nome do usuário.
     * @param emailId O e-mail do usuário.
     * @param roles As funções atribuídas ao usuário.
     */
    public UserModel(String userName, String emailId, String roles) {
        this.userName = userName;
        this.emailId = emailId;
        this.roles = roles;
    }
}
