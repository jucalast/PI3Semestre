package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.PedidoModel;

/**
 * Repositório responsável por realizar operações de persistência
 * para a entidade PedidoModel. Extende JpaRepository, o que permite
 * o uso de métodos padrão para CRUD e consultas personalizadas.
 * 
 * <p>Este repositório fornece métodos para buscar pedidos com base
 * em diferentes critérios, incluindo intervalo de datas e status do pedido.</p>
 * 
 * <p>Os métodos disponíveis incluem:</p>
 * <ul>
 *   <li>findByDataPedidoBetween: Busca pedidos realizados entre duas datas específicas.</li>
 *   <li>findByStatusPedido: Busca pedidos com base no status especificado.</li>
 *   <li>findPedidosByDataInterval: Consulta personalizada para buscar pedidos em um intervalo de datas.</li>
 *   <li>findByUsuarioId: Busca pedidos feitos por um usuário específico.</li>
 * </ul>
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    /**
     * Busca todos os pedidos realizados entre duas datas específicas.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Lista de pedidos realizados entre as datas especificadas.
     */
    List<PedidoModel> findByDataPedidoBetween(String dataInicio, String dataFim);
    
    /**
     * Busca todos os pedidos com um status específico.
     * 
     * @param statusPedido Status do pedido a ser buscado.
     * @return Lista de pedidos que correspondem ao status especificado.
     */
    List<PedidoModel> findByStatusPedido(Integer statusPedido);

    /**
     * Consulta personalizada que busca pedidos realizados entre duas datas específicas.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Lista de pedidos realizados entre as datas especificadas.
     */
    @Query("SELECT p FROM PedidoModel p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<PedidoModel> findPedidosByDataInterval(String dataInicio, String dataFim);
    
    /**
     * Busca todos os pedidos feitos por um usuário específico.
     * 
     * @param usuarioId ID do usuário cujos pedidos devem ser buscados.
     * @return Lista de pedidos associados ao usuário especificado.
     */
    List<PedidoModel> findByUsuarioId(Integer usuarioId);
}
