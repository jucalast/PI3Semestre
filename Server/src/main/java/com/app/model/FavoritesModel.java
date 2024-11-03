package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de entidade representando um item favorito no sistema. Este item conecta um usuário a um produto que ele marcou como favorito.
 * A entidade é persistida na tabela "Favoritos" no banco de dados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Favoritos")
public class FavoritesModel {
    /**
     * Identificador único para o registro de favoritos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O usuário que possui este favorito. A relação muitos-para-um é definida aqui.
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserModel user;

    /**
     * O produto que foi favoritado pelo usuário.
     */
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Produto produto;

    public FavoritesModel(UserModel user, Produto product) {
        this.user = user;
        this.produto = product;
    }
}