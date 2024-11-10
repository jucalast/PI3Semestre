package com.app.repository;

import com.app.model.FavoritesModel;
import com.app.model.UserModel;
import com.app.model.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repositório JPA para operações de banco de dados na entidade FavoritesModel.
 */
@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesModel, Long> {
    /**
     * Encontra todos os favoritos associados a um usuário específico.
     * @param user O usuário cujos favoritos devem ser recuperados.
     * @return Uma lista de objetos FavoritesModel.
     */
    List<FavoritesModel> findByUser(UserModel user);

    /**
     * Verifica se um produto específico já está marcado como favorito por um usuário.
     * @param user O usuário em questão.
     * @param produto O produto em questão.
     * @return true se o produto já é um favorito do usuário, false caso contrário.
     */
    boolean existsByUserAndProduto(UserModel user, Produto produto);

    /**
     * Remove um favorito específico baseado no usuário e produto dados.
     * @param user O usuário de quem o favorito será removido.
     * @param produto O produto que será removido dos favoritos.
     */
    @Transactional
    void deleteByUserAndProduto(UserModel user, Produto produto);
}