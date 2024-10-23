package com.app.controller;

import com.app.model.FavoritesModel;
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
    public ResponseEntity<?> addFavorite(HttpServletRequest request, @RequestParam Long productId) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID must not be null.");
        }

        System.out.println("Received productId: " + productId);

        try {
            if (favoritesService.isProductAlreadyFavorite(authenticatedUser.getId(), productId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is already in favorites.");
            }

            System.out.println("Passing productId to service: " + productId);
            FavoritesModel favorite = favoritesService.addFavorite(authenticatedUser.getId(), productId);
            return ResponseEntity.ok(favorite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding to favorites: " + e.getMessage());
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
}
