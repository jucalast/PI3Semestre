package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representation of a favorite item, which includes user and product identifiers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Favoritos")
public class FavoritesModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    /**
     * Constructs a new FavoritesModel with specified user ID and product ID.
     * @param userId the ID of the user
     * @param productId the ID of the product
     */
    public FavoritesModel(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
