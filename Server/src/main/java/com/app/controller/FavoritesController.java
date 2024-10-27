package com.app.controller;

import com.app.model.FavoritesModel;
import com.app.model.Produto;
import com.app.model.UserModel;
import com.app.service.FavoritesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller for handling requests related to the favorites functionality.
 * It provides endpoints for adding a product to favorites and listing all favorites of a user.
 */
@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    /**
     * Adds a product to the user's favorites.
     *
     * @param request     The HttpServletRequest providing request information.
     * @param productId   The ID of the product to add to favorites.
     * @return ResponseEntity with either a favorite object on success or an error message on failure.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addOrRemoveFavorite(HttpServletRequest request, @RequestParam Long productId) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID must not be null.");
        }

        try {
            // Check if the product is already a favorite
            if (favoritesService.isProductAlreadyFavorite(authenticatedUser.getId(), productId)) {
                // If it is, remove it
                boolean isRemoved = favoritesService.removeFavorite(authenticatedUser.getId(), productId);
                if (isRemoved) {
                    return ResponseEntity.ok("Product " + productId + " removed from favorites.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in favorites.");
                }
            } else {
                // If it's not, add it
                FavoritesModel favorite = favoritesService.addFavorite(authenticatedUser.getId(), productId);
                return ResponseEntity.ok(favorite);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    /**
     * Retrieves a list of all favorites for the authenticated user.
     *
     * @param request The HttpServletRequest providing request information.
     * @return ResponseEntity containing a list of favorites or a relevant error message.
     */
    @GetMapping("/list")
    public ResponseEntity<?> listUserFavorites(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        try {
            List<FavoritesModel> favorites = favoritesService.getUserFavorites(authenticatedUser.getId());
            if (favorites.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(favorites);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorites not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving favorites.");
        }
    }

    @GetMapping("/favorited-products")
    public ResponseEntity<?> listFavoriteProducts(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        try {
            List<Produto> favoriteProducts = favoritesService.getFavoriteProducts(authenticatedUser.getId());
            if (favoriteProducts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(favoriteProducts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorites not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving favorite products.");
        }
    }

    /**
     * Deletes a product from the user's favorites.
     *
     * @param request     The HttpServletRequest providing request information.
     * @param productId   The ID of the product to remove from favorites.
     * @return ResponseEntity with either a success message or an error message.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(HttpServletRequest request, @RequestParam Long productId) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID must not be null.");
        }

        try {
            boolean isRemoved = favoritesService.removeFavorite(authenticatedUser.getId(), productId);
            if (isRemoved) {
                return ResponseEntity.ok("Product " + productId + " removed from favorites.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in favorites.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the product from favorites: " + e.getMessage());
        }
    }

}
