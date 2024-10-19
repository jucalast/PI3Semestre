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
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {

    /**
     * Encontra pedidos dentro de um intervalo de datas.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Uma lista de pedidos com data dentro do intervalo especificado.
     */
    List<PedidoModel> findByDataPedidoBetween(String dataInicio, String dataFim);

    /**
     * Encontra pedidos pelo status.
     * 
     * @param statusPedido O status do pedido (1 a 5).
     * @return Uma lista de pedidos com o status especificado.
     */
    List<PedidoModel> findByStatusPedido(Integer statusPedido);

    /**
     * Encontra pedidos dentro de um intervalo de datas utilizando uma consulta JPQL.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Uma lista de pedidos com data dentro do intervalo especificado.
     */
    @Query("SELECT p FROM PedidoModel p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<PedidoModel> findPedidosByDataInterval(String dataInicio, String dataFim);
}
