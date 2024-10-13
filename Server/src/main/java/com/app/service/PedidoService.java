package com.app.service;

/**
 * Bibliotecas utilizadas:
 * - java.util.*: usado para manipulação de coleções e classes utilitárias.
 * - org.springframework.stereotype.Service: usado para definir a classe como um serviço no Spring.
 * - com.app.model.*: contém as entidades do modelo de dados.
 * - com.app.repository.*: contém as interfaces de repositório para acesso a dados.
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PedidoModel;
import com.app.repository.PedidoRepository;

/**
 * Classe de serviço para a entidade Pedido.
 * Contém a lógica de negócios relacionada aos pedidos.
 */
@Service
public class PedidoService {
    
    /**
     * Injeção de dependência do repositório de pedidos.
     *
     * @param pedidoRepository o repositório de pedidos
     * @see PedidoRepository
     */
    private final PedidoRepository pedidoRepository;

    /**
     * Construtor da classe.
     * @param pedidoRepository o repositório de pedidos
     * @see PedidoRepository
     * Autowired - injeção de dependência
     */
    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Salva um novo pedido no banco de dados.
     *
     * @param pedido o pedido a ser salvo
     * @return o pedido salvo
     */
    public PedidoModel salvarPedido(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }

    /**
     * Lista todos os pedidos.
     *
     * @return lista de todos os pedidos
     * findAll - Lista todos os itens da tabela
     */
    public List<PedidoModel> listarPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Obtém um pedido por ID.
     *
     * @param id o ID do pedido a ser obtido
     * @return o pedido encontrado, ou vazio se não encontrado
     * findById - Busca um item pelo ID
     */
    public Optional<PedidoModel> obterPedidoPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Atualiza um pedido existente.
     *
     * @param id     o ID do pedido a ser atualizado
     * @param pedido os novos dados do pedido
     * @return o pedido atualizado se encontrado, ou um Optional vazio se não encontrado
     */
    public Optional<PedidoModel> atualizarPedido(Integer id, PedidoModel pedido) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id);
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }

    /**
     * Obtém pedidos pelo status.
     *
     * @param status o status do pedido a ser buscado
     * @return lista de pedidos com o status especificado
     */
    public List<PedidoModel> encontrarPorStatusPedido(Integer status) {
        return pedidoRepository.findByStatusPedido(status);
    }
    /**
     * Atualiza o status de um pedido.
     *
     * @param id     o ID do pedido a ser atualizado
     * @param status o novo status do pedido
     * @return o pedido atualizado se encontrado, ou um Optional vazio se não encontrado
     * atualizarStatus - Atualiza o status do pedido
     */
    public Optional<PedidoModel> atualizarStatus(Integer id, Integer status) {
        if (pedidoRepository.existsById(id)) {
            return Optional.of(pedidoRepository.atualizarStatus(id, status));
        }
        return Optional.empty();
    } 
    /**
     * Coluna statusPedido, armazena o status do pedido.
     * Valores permitidos:
     * <ul>
     *     <li>1 - Novo</li>
     *     <li>2 - Processando</li>
     *     <li>3 - Enviado</li>
     *     <li>4 - Entregue</li>
     *     <li>5 - Cancelado</li>
     * </ul>
     */
}
