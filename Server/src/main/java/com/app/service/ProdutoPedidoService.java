package com.app.service;

/**
 * Bibliotecas utilizadas:
 * 
 * - java.util.List: Interface que representa uma coleção ordenada de elementos, permitindo
 *   operações como adição, remoção e acesso a elementos por índice.
 * - java.util.Optional: Classe que representa um valor que pode ou não estar presente,
 *   evitando NullPointerExceptions e fornecendo métodos para lidar com a ausência de valor.
 * - org.springframework.beans.factory.annotation.Autowired: Anotação utilizada para injeção
 *   de dependência em classes do Spring, facilitando a gestão de objetos e seus ciclos de vida.
 * - org.springframework.stereotype.Service: Anotação que marca a classe como um serviço,
 *   permitindo que o Spring a detecte e a registre como um bean.
 * - com.app.model.ProdutoPedidoModel: Classe que representa a associação entre um pedido e um produto, incluindo quantidade
 *   e subtotal.
 * - com.app.repository.ProdutoPedidoRepository: Interface que fornece métodos para interagir com o banco de dados
 *   relacionados à entidade ProdutoPedidoModel.
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.ProdutoPedidoModel;
import com.app.repository.ProdutoPedidoRepository;

/**
 * Serviço para operações relacionadas à entidade ProdutoPedidoModel.
 * Este serviço contém a lógica de negócios e se comunica com o repositório
 * para realizar operações de CRUD e outras ações personalizadas.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Service
public class ProdutoPedidoService {

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    /**
     * Lista todos os produtos dos pedidos cadastrados.
     * 
     * @return Lista de ProdutoPedidoModel.
     */
    public List<ProdutoPedidoModel> listarTodos() {
        return produtoPedidoRepository.findAll();
    }

    /**
     * Busca um produto de pedido específico pelo seu ID.
     * 
     * @param id ID do produto no pedido.
     * @return Um Optional com ProdutoPedidoModel se encontrado, ou vazio se não encontrado.
     */
    public Optional<ProdutoPedidoModel> buscarPorId(Integer id) {
        return produtoPedidoRepository.findById(id);
    }

    /**
     * Salva um novo produto no pedido ou atualiza um produto existente.
     * 
     * @param produtoPedido O ProdutoPedidoModel a ser salvo ou atualizado.
     * @return O ProdutoPedidoModel salvo ou atualizado.
     */
    public ProdutoPedidoModel salvarOuAtualizar(ProdutoPedidoModel produtoPedido) {
        return produtoPedidoRepository.save(produtoPedido);
    }

    /**
     * Exclui um produto de pedido específico pelo seu ID.
     * 
     * @param id ID do produto no pedido a ser excluído.
     */
    public void excluirPorId(Integer id) {
        produtoPedidoRepository.deleteById(id);
    }

    /**
     * Lista todos os produtos relacionados a um pedido específico.
     * 
     * @param pedidoId ID do pedido.
     * @return Lista de produtos relacionados ao pedido.
     */
    public List<ProdutoPedidoModel> buscarPorPedidoId(Integer pedidoId) {
        return produtoPedidoRepository.findByPedidoId(pedidoId);
    }

    /**
     * Exclui todos os produtos relacionados a um pedido específico.
     * 
     * @param pedidoId ID do pedido.
     */
    public void excluirPorPedidoId(Integer pedidoId) {
        produtoPedidoRepository.deleteByPedidoId(pedidoId);
    }
}
