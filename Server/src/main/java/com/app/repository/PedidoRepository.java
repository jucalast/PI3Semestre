package com.app.repository;
/**
 * Bibliotecas utilizadas:
 * com.app.model.* - usado para mapear a entidade no banco de dados.
 * org.springframework.data.jpa.repository.JpaRepository - usado para mapear a entidade no banco de dados.
 * org.springframework.stereotype.Repository - usado para mapear a entidade no banco de dados.
 * java.util.* - usado para mapear a entidade no banco de dados.
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.PedidoModel;
/**
 * Interface PedidoRepository serve como repositório para a entidade PedidoModel.
 * 
 * <p>Esta interface herda de JpaRepository, o que permite realizar operações
 * CRUD básicas e consultas personalizadas no banco de dados.
 * 
 * @author Kairo
 * @version 1.0
 * @since 12-10-2024
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {
/**
 * Busca um pedido no banco de dados pelo ID.
 * 
 * @param id O ID do pedido a ser buscado.
 * @return O modelo do pedido correspondente ao ID fornecido, ou null se não for encontrado.
 */
   public PedidoModel findByid(Integer id);
/**
 * Busca um pedido no banco de dados pelo status.
 * 
 * @param status O status do pedido a ser buscado.
 * @return A lista de pedidos correspondentes ao status fornecido.
 */
   public List<PedidoModel> findByStatusPedido(Integer status);
   /**
    * Busca um pedido no banco de dados pelo ID do usuario.
    * 
    * @param id O ID do usuario a ser buscado.
    * @return A lista de pedidos correspondentes ao ID do usuario fornecido.
    */
   public List<PedidoModel> findByUsuarioId(Integer id);
 /**
  * Busca pedidos pelo estado da entrega.
  * 
  * @param estado O estado da entrega a ser buscado.
  * @return A lista de pedidos correspondentes ao estado citado
  */
   public List<PedidoModel> findByEstado(String estado);
   /**
    * Atualiza o status de um pedido pelo id
    * 
    * @param id O ID do pedido a ser atualizado
    * @param status O novo status do pedido
    * @return O pedido atualizado se encontrado, ou um Optional vazio se não encontrado
    */
   @Modifying
    @Transactional
    @Query("UPDATE PedidoModel p SET p.statusPedido = ?2 WHERE p.id = ?1")
    PedidoModel atualizarStatus(Integer id, Integer status);
}
