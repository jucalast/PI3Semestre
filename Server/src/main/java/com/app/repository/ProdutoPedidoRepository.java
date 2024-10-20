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
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedidoModel, Integer> {

    List<ProdutoPedidoModel> findByPedidoId(Integer pedidoId);
    
    void deleteByPedidoId(Integer pedidoId);
    
    // Adicionando método para excluir todos os produtos de um pedido (opcional)
    void deleteByProdutoId(Integer produtoId);
}
