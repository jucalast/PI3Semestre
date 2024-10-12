package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Pedido;
import com.app.repository.PedidoRepository;

/**
 * Classe de serviço para a entidade Pedido.
 */
@Service
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Lista todos os pedidos.
     * @return lista de pedidos
     */
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Busca um pedido pelo ID.
     * @param id ID do pedido
     * @return pedido encontrado ou vazio se não existir
     */
    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Cria um novo pedido.
     * @param pedido detalhes do novo pedido
     * @return pedido criado
     */
    public Pedido criar(Pedido pedido) {
        pedido.setDataAtualizacao(null); // Definir a data de atualização conforme necessário
        return pedidoRepository.save(pedido);
    }

    /**
     * Atualiza um pedido existente.
     * @param id ID do pedido a ser atualizado
     * @param pedido detalhes atualizados do pedido
     * @return pedido atualizado ou vazio se o pedido não for encontrado
     */
    public Optional<Pedido> atualizar(Integer id, Pedido pedido) {
        return pedidoRepository.findById(id).map(pedidoExistente -> {
            pedidoExistente.setUsuarioId(pedido.getUsuarioId());
            pedidoExistente.setDataPedido(pedido.getDataPedido());
            pedidoExistente.setStatusPedido(pedido.getStatusPedido());
            pedidoExistente.setTotal(pedido.getTotal());
            pedidoExistente.setEnderecoId(pedido.getEnderecoId());
            pedidoExistente.setObservacoes(pedido.getObservacoes());
            pedidoExistente.setDataAtualizacao(pedido.getDataAtualizacao());
            return pedidoRepository.save(pedidoExistente);
        });
    }

    /**
     * Deleta um pedido pelo ID.
     * @param id ID do pedido
     * @return true se o pedido foi deletado, false caso contrário
     */
    public boolean deletar(Integer id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

