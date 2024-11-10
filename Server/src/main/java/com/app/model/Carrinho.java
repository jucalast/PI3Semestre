package com.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a entidade Carrinho para a aplicação.
 * Esta classe está mapeada para a tabela 'Carrinho' no bando de dados
 *
 * @author Ricardo L. Ferreira
 */

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carrinho {

    /**
     * Identificador único do carrinho
     * Este campo é gerado e incrementado automaticamente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relação N:1 com UserModel
     */
    @ManyToOne
    @JsonProperty("id_user")
    @JoinColumn(name = "id_user")
    private UserModel userModel;

    /**
     * Relação N:1 com Produto
     */
    @JsonIgnore
    @ManyToOne
    @JsonProperty("id_produto")
    @JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false)
    private Produto produto;

    /**
     * Quantidade unitária do item presente no carrinho
     */
    @Column(nullable = false, name = "quantidade")
    private int quantidade;


    public Carrinho(Long userId, Long productId) {

    }

    public Carrinho(UserModel userModel, Produto produto, int quantidade) {
        this.userModel = userModel;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
