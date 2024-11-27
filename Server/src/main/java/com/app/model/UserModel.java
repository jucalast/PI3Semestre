package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Representa a entidade Usuário para a aplicação.
 * Esta classe está mapeada para a tabela 'TB_USER' no banco de dados.
 *
 * @author Giovanni
 * @version 1.3
 * @since 2024-10-13
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
     * Nome completo do usuário.
     * Não pode ser nulo.
     */
    @Column(nullable = false, name = "USER_NAME")
    private String userName;

    /**
     * Endereço de e-mail do usuário.
     * Deve ser único no banco de dados.
     */
    @Column(nullable = false, name = "EMAIL_ID", unique = true)
    private String emailId;

    /**
     * CPF do usuário.
     * Deve ser único no banco de dados.
     */
    @Column(nullable = true, name = "CPF", unique = true)
    private String cpf;

    /**
     * Número de telefone do usuário.
     * Este campo é opcional.
     */
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    /**
     * Senha do usuário.
     */
    @Column(nullable = true, name = "PASSWORD")
    private String password;

    /**
     * Funções atribuídas ao usuário.
     * Não pode ser nulo.
     */
    @Column(nullable = false, name = "ROLES")
    private String roles;

    /**
     * URL da imagem de perfil do usuário.
     */
    @Column(name = "PROFILE_PIC", columnDefinition = "TEXT") 
    private String profilePic;

    /**
     * Relação N:N com AddressModel.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserAddress> userAddresses;

    /**
     * Relação N:N com Carrinho
     */
    @ManyToMany
    @JoinTable(
            name = "carrinho",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private Set<Produto> produtos;

    public UserModel(Long id) {
        this.id = id;
    }

}
