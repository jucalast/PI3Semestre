package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de entidade representando um item favorito, que associa um usuário a um produto que ele marcou como favorito.
 * Esta classe é mapeada para uma tabela no banco de dados através de anotações JPA.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Favoritos")
public class FavoritesModel {
    /**
     * A chave primária do registro de favoritos.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * O ID do usuário que marcou o item como favorito.
     * Este campo é vinculado à entidade do usuário e é obrigatório.
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * O ID do produto marcado como favorito pelo usuário.
     * Este campo é vinculado à entidade do produto e é obrigatório.
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * Constrói um novo modelo de favoritos com ID de usuário e ID de produto especificados.
     * @param userId     O ID do usuário.
     * @param productId  O ID do produto.
     */
    public FavoritesModel(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
