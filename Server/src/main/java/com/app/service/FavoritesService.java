package com.app.service;

import com.app.model.FavoritesModel;
import com.app.repository.FavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing favorite items, providing methods to add and retrieve favorites.
 */
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    /**
     * Adds a new favorite for a user.
     * @param userId The ID of the user.
     * @param productId The ID of the product.
     * @return The saved favorite model.
     */
    public FavoritesModel addFavorite(Long userId, Long productId) {
        System.out.println("Service Layer - UserId: " + userId + ", ProductId: " + productId);
        FavoritesModel favorite = new FavoritesModel(userId, productId);
        return favoritesRepository.save(favorite);
    }

    /**
     * Retrieves all favorite items of a user.
     * @param userId The ID of the user.
     * @return A list of favorite models.
     */
    public List<FavoritesModel> getUserFavorites(Long userId) {
        return favoritesRepository.findByUserId(userId);
    }

    /**
     * Checks if a product is already marked as favorite by a user.
     * @param userId The user's ID.
     * @param productId The product's ID.
     * @return true if the product is already a favorite, false otherwise.
     */
    public boolean isProductAlreadyFavorite(Long userId, Long productId) {
        return favoritesRepository.existsByUserIdAndProductId(userId, productId);
    }
}
