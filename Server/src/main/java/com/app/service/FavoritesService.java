package com.app.service;

import com.app.model.FavoritesModel;
import com.app.model.Produto;
import com.app.model.UserModel;
import com.app.repository.FavoritesRepository;
import com.app.repository.ProdutoRepository;
import com.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar a lógica de negócios relacionada aos favoritos dos usuários.
 */
@Service
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository, ProdutoRepository produtoRepository, UserRepository userRepository) {
        this.favoritesRepository = favoritesRepository;
        this.produtoRepository = produtoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adiciona um produto aos favoritos de um usuário.
     * @param userId O identificador do usuário.
     * @param productId O identificador do produto.
     * @return O objeto FavoritesModel recém-criado e salvo.
     */
    @Transactional
    public FavoritesModel addFavorite(Long userId, Long productId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Produto product = produtoRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        FavoritesModel favorite = new FavoritesModel(user, product);
        return favoritesRepository.save(favorite);
    }

    /**
     * Recupera todos os favoritos de um usuário.
     * @param userId O identificador do usuário.
     * @return Uma lista de objetos FavoritesModel associados ao usuário.
     */
    public List<FavoritesModel> getUserFavorites(Long userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return favoritesRepository.findByUser(user);
    }

    /**
     * Verifica se um produto é um favorito de um usuário.
     * @param userId O identificador do usuário.
     * @param productId O identificador do produto.
     * @return true se o produto for um favorito do usuário, caso contrário, false.
     */
    public boolean isProductAlreadyFavorite(Long userId, Long productId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Produto product = produtoRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return favoritesRepository.existsByUserAndProduto(user, product);
    }

    /**
     * Recupera os produtos favoritos de um usuário, listando apenas os produtos.
     * @param userId O identificador do usuário.
     * @return Uma lista de produtos favoritos do usuário.
     */
    public List<Produto> getFavoriteProducts(Long userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<FavoritesModel> favorites = favoritesRepository.findByUser(user);
        return favorites.stream()
                .map(FavoritesModel::getProduto)
                .collect(Collectors.toList());
    }

    /**
     * Remove um produto específico dos favoritos de um usuário.
     * @param userId O identificador do usuário.
     * @param productId O identificador do produto a ser removido.
     * @return true se o favorito foi removido com sucesso, false caso contrário.
     */
    public boolean removeFavorite(Long userId, Long productId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Produto product = produtoRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        if (favoritesRepository.existsByUserAndProduto(user, product)) {
            favoritesRepository.deleteByUserAndProduto(user, product);
            return true;
        }
        return false;
    }

    /**
     * Conta o número de favoritos para cada produto.
     * @return Um mapa onde a chave é o ID do produto e o valor é o número de favoritos.
     */
    public Map<Long, Long> countFavoritesByProduct() {
        return favoritesRepository.findAll().stream()
                .collect(Collectors.groupingBy(favorite -> favorite.getProduto().getId(), Collectors.counting()));
    }
}
