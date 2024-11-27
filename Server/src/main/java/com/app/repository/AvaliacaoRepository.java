package com.app.repository;

import com.app.model.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade AvaliacaoModel.
 * Esta interface herda JpaRepository para fornecer operações CRUD
 * e consultas personalizadas para a tabela 'avaliacao'.
 *
 * @since 1.0
 */
@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {

    /**
     * Encontra todas as avaliações baseadas no ID do ProdutoPedido.
     *
     * @param produtoPedidoId ID do ProdutoPedido para o qual buscar as avaliações.
     * @return Uma lista de AvaliacaoModel contendo as avaliações encontradas ou uma lista vazia caso não existam avaliações.
     */
    List<AvaliacaoModel> findByProdutoPedidoId(Integer produtoPedidoId);
}
