package com.app.repository;

import com.app.model.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repositório para a entidade AvaliacaoModel.
 * Esta interface herda JpaRepository para fornecer operações CRUD
 * e consultas personalizadas para a tabela 'avaliacao'.
 *
 * @since 1.0
 */
@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Integer> {

    /**
     * Encontra uma avaliação baseada no ID do ProdutoPedido.
     *
     * @param produtoPedidoId ID do ProdutoPedido para o qual buscar a avaliação.
     * @return Um Optional contendo a avaliação encontrada ou vazio caso não exista.
     */
    Optional<AvaliacaoModel> findByProdutoPedidoId(Integer produtoPedidoId);
}
