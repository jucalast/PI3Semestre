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
 * Classe controladora para lidar com operações relacionadas à funcionalidade de favoritos do usuário.
 * Fornece endpoints RESTful para adicionar, remover e listar produtos favoritos de um usuário.
 * Este controlador realiza a verificação de autenticação, garantindo que uma sessão de usuário exista antes de permitir modificações nos favoritos.
 */
@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    /**
     * Objeto de serviço para gerenciar operações de favoritos. É automaticamente injetado pelo Spring.
     */
    @Autowired
    private FavoritesService favoritesService;

    /**
     * Manipula a solicitação para adicionar ou remover um produto da lista de favoritos do usuário.
     * O produto é adicionado se atualmente não for um favorito; caso contrário, é removido.
     *
     * @param request    O HttpServletRequest que fornece informações da sessão.
     * @param productId  O ID do produto a ser adicionado ou removido.
     * @return           Uma ResponseEntity com um objeto favorito na adição bem-sucedida,
     *                   uma mensagem de sucesso na remoção ou uma mensagem de erro se a operação falhar.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addOrRemoveFavorite(HttpServletRequest request, @RequestParam Long productId) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do produto não pode ser nulo.");
        }

        try {
            // Verifica se o produto já é um favorito
            if (favoritesService.isProductAlreadyFavorite(authenticatedUser.getId(), productId)) {
                // Se for, remove
                boolean isRemoved = favoritesService.removeFavorite(authenticatedUser.getId(), productId);
                if (isRemoved) {
                    return ResponseEntity.ok("Produto " + productId + " removido dos favoritos.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado nos favoritos.");
                }
            } else {
                // Se não for, adiciona
                FavoritesModel favorite = favoritesService.addFavorite(authenticatedUser.getId(), productId);
                return ResponseEntity.ok(favorite);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro: " + e.getMessage());
        }
    }

    /**
     * Recupera todos os favoritos para o usuário autenticado.
     *
     * @param request O HttpServletRequest que fornece informações da sessão.
     * @return        Uma ResponseEntity contendo uma lista de objetos favoritos, ou uma mensagem de erro apropriada.
     */
    @GetMapping("/list")
    public ResponseEntity<?> listUserFavorites(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        try {
            List<FavoritesModel> favorites = favoritesService.getUserFavorites(authenticatedUser.getId());
            if (favorites.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(favorites);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favoritos não encontrados.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao recuperar os favoritos.");
        }
    }

    /**
     * Recupera todos os produtos favoritos para o usuário autenticado, detalhando as informações do produto.
     *
     * @param request O HttpServletRequest que fornece informações da sessão.
     * @return        Uma ResponseEntity contendo uma lista de produtos, ou uma mensagem de erro apropriada.
     */
    @GetMapping("/favorited-products")
    public ResponseEntity<?> listFavoriteProducts(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        try {
            List<Produto> favoriteProducts = favoritesService.getFavoriteProducts(authenticatedUser.getId());
            if (favoriteProducts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(favoriteProducts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favoritos não encontrados.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao recuperar produtos favoritos.");
        }
    }

    /**
     * Exclui um produto específico dos favoritos do usuário.
     *
     * @param request    O HttpServletRequest que fornece informações da sessão.
     * @param productId  O ID do produto a ser removido dos favoritos.
     * @return           Uma ResponseEntity com uma mensagem de sucesso ou uma mensagem de erro.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(HttpServletRequest request, @RequestParam Long productId) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do produto não pode ser nulo.");
        }

        try {
            boolean isRemoved = favoritesService.removeFavorite(authenticatedUser.getId(), productId);
            if (isRemoved) {
                return ResponseEntity.ok("Produto " + productId + " removido dos favoritos.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado nos favoritos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao remover o produto dos favoritos: " + e.getMessage());
        }
    }
}
