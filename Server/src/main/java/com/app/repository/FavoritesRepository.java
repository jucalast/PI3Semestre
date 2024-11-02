package com.app.repository;

import com.app.model.FavoritesModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface do repositório Spring Data JPA para entidades do modelo de favoritos.
 * Fornece métodos para realizar operações CRUD na tabela de favoritos no banco de dados,
 * especificamente encontrando por ID de usuário, verificando a existência por ID de usuário e produto, e excluindo por ID de usuário e produto.
 */
@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesModel, Long> {
    /**
     * Encontra todos os registros de favoritos associados a um ID de usuário específico.
     * @param userId O ID do usuário para procurar favoritos.
     * @return Uma lista de instâncias de FavoritesModel.
     */
    List<FavoritesModel> findByUserId(Long userId);

    /**
     * Verifica se um produto específico já está marcado como favorito por um usuário específico.
     * @param userId O ID do usuário.
     * @param productId O ID do produto.
     * @return verdadeiro se o favorito existir, falso caso contrário.
     */
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    /**
     * Remove um item favorito por ID de usuário e ID de produto.
     * Este método é transacional para garantir a consistência do banco de dados durante a operação de exclusão.
     *
     * @param userId     O ID do usuário.
     * @param productId  O ID do produto.
     */
    @Transactional
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
