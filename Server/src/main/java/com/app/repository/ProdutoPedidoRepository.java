package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.ProdutoPedidoModel;

/**
 * Repositório para a entidade ProdutoPedidoModel.
 * Esta interface herda JpaRepository para fornecer operações CRUD
 * e consultas personalizadas para a tabela 'produtoPedido'.
 * 
 * <p>Esta interface permite a execução de operações de persistência de dados, como
 * salvar, atualizar, excluir e consultar registros relacionados aos produtos de um pedido.</p>
 * 
 * <p>Os métodos específicos disponíveis incluem:</p>
 * <ul>
 *   <li>findByPedidoId: Busca todos os produtos de um pedido específico.</li>
 *   <li>deleteByPedidoId: Exclui todos os produtos relacionados a um pedido específico.</li>
 *   <li>deleteByProdutoId: Exclui todos os produtos de um pedido com base no ID do produto.</li>
 * </ul>
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedidoModel, Integer> {

    /**
     * Busca todos os produtos associados a um pedido específico pelo seu ID.
     * 
     * @param pedidoId ID do pedido a ser buscado.
     * @return Lista de produtos associados ao pedido.
     */
    List<ProdutoPedidoModel> findByPedidoId(Integer pedidoId);

    /**
     * Busca todos os pedidos associados a um produto específico pelo seu ID.
     *
     * @param produtoId ID do produto a ser buscado.
     * @return Lista de ProdutoPedidoModel contendo os pedidos associados ao produto.
     */
    List<ProdutoPedidoModel> findByProdutoId(Integer produtoId);
}
