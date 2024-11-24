package com.app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.CartaoModel;

/**
 * Interface para operações de repositório com a entidade CartaoModel.
 */
@Repository
public interface CartaoRepository extends JpaRepository<CartaoModel, String> {

    /**
     * Busca um cartão pelo seu número, fornecendo um opcional que pode ou não conter o cartão.
     *
     * @param numeroCartao O número do cartão a ser buscado.
     * @return Um Optional contendo o CartaoModel, se encontrado.
     */
    Optional<CartaoModel> findByNumeroCartao(String numeroCartao);

    /**
     * Busca todos os cartões associados a um determinado ID de usuário.
     *
     * @param userId ID do usuário proprietário dos cartões.
     * @return Lista de cartões associados ao usuário.
     */
    @Query("SELECT c FROM CartaoModel c WHERE c.user.id = :userId")
    List<CartaoModel> findByUserId(Long userId);
}
