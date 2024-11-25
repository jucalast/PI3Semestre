package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.PedidoModel;
import com.app.model.UserModel;

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

}
