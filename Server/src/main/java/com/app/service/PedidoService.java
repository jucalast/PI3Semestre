package com.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PedidoModel;
import com.app.repository.PedidoRepository;

/**
 * Serviço responsável por realizar as operações de negócios
 * relacionadas à entidade PedidoModel. Este serviço interage com o 
 * PedidoRepository para acessar os dados de persistência.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Busca todos os pedidos armazenados no banco de dados.
     * 
     * @return Uma lista com todos os pedidos.
     */
    public List<PedidoModel> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Busca um pedido pelo seu ID.
     * 
     * @param id O ID do pedido.
     * @return O pedido correspondente ao ID, ou null se não encontrado.
     */
    public PedidoModel buscarPedidoPorId(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    /**
     * Salva ou atualiza um pedido no banco de dados.
     * 
     * @param pedido O pedido a ser salvo ou atualizado.
     * @return O pedido salvo ou atualizado.
     */
    public PedidoModel salvarOuAtualizarPedido(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }

    /**
     * Exclui um pedido pelo seu ID.
     * 
     * @param id O ID do pedido a ser excluído.
     */
    public void excluirPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }

    /**
     * Busca todos os pedidos dentro de um intervalo de datas.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Uma lista de pedidos com data dentro do intervalo especificado.
     */
    public List<PedidoModel> buscarPedidosPorIntervaloDeDatas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pedidoRepository.findByDataPedidoBetween(dataInicio, dataFim);
    }

    /**
     * Busca todos os pedidos com o status especificado.
     * 
     * @param statusPedido O status do pedido.
     * @return Uma lista de pedidos com o status especificado.
     */
    public List<PedidoModel> buscarPedidosPorStatus(Integer statusPedido) {
        return pedidoRepository.findByStatusPedido(statusPedido);
    }

    /**
     * Busca todos os pedidos dentro de um intervalo de datas utilizando uma consulta customizada.
     * 
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Uma lista de pedidos com data dentro do intervalo especificado.
     */
    public List<PedidoModel> buscarPedidosPorConsultaCustomizada(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pedidoRepository.findPedidosByDataInterval(dataInicio, dataFim);
    }
}
