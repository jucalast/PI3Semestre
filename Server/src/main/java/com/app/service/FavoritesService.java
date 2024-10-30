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
 * Classe de serviço para gerenciar itens favoritos.
 * Fornece métodos para adicionar e remover favoritos, verificar se um produto é um favorito e recuperar todos os favoritos ou produtos favoritos de um usuário.
 */
@Service
public class FavoritesService {
    /**
     * Repositório para gerenciar operações CRUD em itens favoritos.
     */
    private final FavoritesRepository favoritesRepository;

    /**
     * Repositório para acessar informações de produtos, usado para buscar produtos favoritos.
     */
    private final ProdutoRepository produtoRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository, ProdutoRepository produtoRepository) {
        this.favoritesRepository = favoritesRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Adiciona um novo favorito para um usuário.
     *
     * @param userId     O ID do usuário.
     * @param productId  O ID do produto.
     * @return           A instância de FavoritesModel salva.
     */
    public FavoritesModel addFavorite(Long userId, Long productId) {
        System.out.println("Service Layer - UserId: " + userId + ", ProductId: " + productId);
        FavoritesModel favorite = new FavoritesModel(userId, productId);
        return favoritesRepository.save(favorite);
    }

    /**
     * Recupera todos os itens favoritos de um usuário.
     *
     * @param userId  O ID do usuário.
     * @return        Uma lista de instâncias de FavoritesModel.
     */
    public List<FavoritesModel> getUserFavorites(Long userId) {
        return favoritesRepository.findByUserId(userId);
    }

    /**
     * Verifica se um produto já está marcado como favorito por um usuário.
     *
     * @param userId     O ID do usuário.
     * @param productId  O ID do produto.
     * @return           verdadeiro se o produto já for um favorito, falso caso contrário.
     */
    public boolean isProductAlreadyFavorite(Long userId, Long productId) {
        return favoritesRepository.existsByUserIdAndProductId(userId, productId);
    }

    /**
     * Recupera todos os produtos que um usuário marcou como favoritos.
     *
     * @param userId  O ID do usuário.
     * @return        Uma lista de instâncias de Produto representando os produtos favoritos.
     */
    public List<Produto> getFavoriteProducts(Long userId) {
        List<FavoritesModel> favorites = getUserFavorites(userId);
        return favorites.stream()
                .map(favorite -> produtoRepository.findById(favorite.getProductId()).orElse(null))
                .collect(Collectors.toList());
    }

    /**
     * Remove um item favorito de um usuário.
     *
     * @param userId     O ID do usuário.
     * @param productId  O ID do produto a ser removido.
     * @return           verdadeiro se o favorito foi removido com sucesso, falso caso contrário.
     */
    public boolean removeFavorite(Long userId, Long productId) {
        if (favoritesRepository.existsByUserIdAndProductId(userId, productId)) {
            favoritesRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }
        return false;
    }
}
