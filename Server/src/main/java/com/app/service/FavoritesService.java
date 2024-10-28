package com.app.service;

import com.app.model.FavoritesModel;
import com.app.model.Produto;
import com.app.repository.FavoritesRepository;
import com.app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing favorite items, providing methods to add and retrieve favorites.
 */
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final ProdutoRepository produtoRepository;



    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository, ProdutoRepository produtoRepository) {
        this.favoritesRepository = favoritesRepository;
        this.produtoRepository = produtoRepository;
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



    public List<Produto> getFavoriteProducts(Long userId) {
        List<FavoritesModel> favorites = getUserFavorites(userId);
        return favorites.stream()
                .map(favorite -> produtoRepository.findById(favorite.getProductId()).orElse(null))
                .collect(Collectors.toList());
    }

    /**
     * Removes a favorite from the user.
     * @param userId The ID of the user.
     * @param productId The ID of the product to remove.
     * @return true if the favorite was successfully removed, false otherwise.
     */
    public boolean removeFavorite(Long userId, Long productId) {
        if (favoritesRepository.existsByUserIdAndProductId(userId, productId)) {
            favoritesRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }
        return false;
    }

}
