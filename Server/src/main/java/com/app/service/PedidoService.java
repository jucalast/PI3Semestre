package com.app.service;

/**
 * Bibliotecas utilizadas:
 * 
 * - java.math.BigDecimal: Classe que representa um número de ponto fixo e que permite operações aritméticas
 *   com precisão arbitrária, ideal para operações financeiras.
 * - java.util.List: Interface que representa uma coleção ordenada de elementos, permitindo
 *   operações como adição, remoção e acesso a elementos por índice.
 * - org.springframework.beans.factory.annotation.Autowired: Anotação utilizada para injeção
 *   de dependência em classes do Spring, facilitando a gestão de objetos e seus ciclos de vida.
 * - org.springframework.stereotype.Service: Anotação que marca a classe como um serviço,
 *   permitindo que o Spring a detecte e a registre como um bean.
 * - org.springframework.transaction.annotation.Transactional: Anotação que indica que o método deve ser executado
 *   dentro de uma transação, garantindo a atomicidade das operações.
 * - com.app.DTO.PedidoDTO: Classe DTO (Data Transfer Object) usada para transferir dados de pedidos entre camadas.
 * - com.app.DTO.ProdutoPedidoDTO: Classe DTO que representa informações sobre produtos em um pedido.
 * - com.app.model.PedidoModel: Classe que representa o modelo de dados para pedidos, contendo atributos e comportamentos
 *   relacionados a um pedido específico.
 * - com.app.model.Produto: Classe que representa o modelo de dados para produtos, contendo atributos e comportamentos
 *   relacionados a um produto específico.
 * - com.app.model.ProdutoPedidoModel: Classe que representa a associação entre um pedido e um produto, incluindo quantidade
 *   e subtotal.
 * - com.app.repository.PedidoRepository: Interface que fornece métodos para interagir com o banco de dados relacionados
 *   à entidade PedidoModel, estendendo JpaRepository.
 * - com.app.repository.ProdutoPedidoRepository: Interface que fornece métodos para interagir com o banco de dados
 *   relacionados à entidade ProdutoPedidoModel.
 * - com.app.repository.ProdutoRepository: Interface que fornece métodos para interagir com o banco de dados
 *   relacionados à entidade Produto.
 */
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.DTO.PedidoDTO;
import com.app.DTO.ProdutoPedidoDTO;
import com.app.model.PedidoModel;
import com.app.model.Produto;
import com.app.model.ProdutoPedidoModel;
import com.app.repository.PedidoRepository;
import com.app.repository.ProdutoPedidoRepository;
import com.app.repository.ProdutoRepository;

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

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cria um novo pedido e os produtos associados a ele.
     * 
     * @param pedidoDTO O DTO contendo as informações do pedido e dos produtos.
     * @return O pedido salvo com os produtos associados.
     */
    @Transactional
    public PedidoModel criarPedidoComProdutos(PedidoDTO pedidoDTO) {
        // Criar o pedido
        PedidoModel pedido = new PedidoModel();
        pedido.setUsuarioId(pedidoDTO.getUsuarioId());
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setStatusPedido(pedidoDTO.getStatusPedido());
        pedido.setTotal(calcularTotal(pedidoDTO.getProdutos()));
        pedido.setObservacoes(pedidoDTO.getObservacoes());
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setCep(pedidoDTO.getCep());
        pedido.setCidade(pedidoDTO.getCidade());
        pedido.setBairro(pedidoDTO.getBairro());
        pedido.setComplemento(pedidoDTO.getComplemento());
        pedido.setNumero(pedidoDTO.getNumero());
        pedido.setRua(pedidoDTO.getRua());

        // Salvar o pedido
        PedidoModel pedidoSalvo = pedidoRepository.save(pedido);

        // Criar os produtos do pedido
        for (ProdutoPedidoDTO produtoDTO : pedidoDTO.getProdutos()) {
            Produto produto = produtoRepository.findById(produtoDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produtoDTO.getProdutoId()));

            // Verifique se a quantidade solicitada está disponível no estoque
            if (produto.getQuantidadeEstoque() < produtoDTO.getQuantidade()) {
                throw new RuntimeException("Quantidade solicitada para o produto " + produto.getNome() + " não está disponível no estoque.");
            }

            ProdutoPedidoModel produtoPedido = new ProdutoPedidoModel();
            produtoPedido.setPedido(pedidoSalvo);
            produtoPedido.setProduto(produto);
            produtoPedido.setQuantidade(produtoDTO.getQuantidade());
            produtoPedido.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(produtoDTO.getQuantidade())));

            // Salvar o produto do pedido
            produtoPedidoRepository.save(produtoPedido);

            // Atualiza a quantidade no estoque do produto
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - produtoDTO.getQuantidade());
            produtoRepository.save(produto); // Salva as alterações no produto
        }

        return pedidoSalvo;
    }

    /**
     * Calcula o total do pedido com base nos produtos.
     * 
     * @param produtos A lista de produtos do pedido.
     * @return O total calculado.
     */
    private BigDecimal calcularTotal(List<ProdutoPedidoDTO> produtos) {
        BigDecimal total = BigDecimal.ZERO;
        for (ProdutoPedidoDTO produtoDTO : produtos) {
            total = total.add(produtoDTO.getSubtotal());
        }
        return total;
    }

    /**
     * Este método irá listar todos os pedidos.
     * 
     * @return Lista de todos os pedidos.
     */
    public List<PedidoModel> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Este método busca um pedido por ID.
     * 
     * @param id ID do pedido.
     * @return O pedido encontrado ou null se não encontrado.
     */
    public PedidoModel getPedidoById(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    /**
     * Este método atualiza um pedido existente.
     * 
     * @param id ID do pedido a ser atualizado.
     * @param pedido Os dados do pedido para atualização.
     * @return O pedido atualizado.
     */
    @Transactional
    public PedidoModel updatePedido(Long id, PedidoModel pedido) {
        PedidoModel pedidoExistente = getPedidoById(id); // Busca o pedido existente
        pedidoExistente.setUsuarioId(pedido.getUsuarioId());
        pedidoExistente.setDataPedido(pedido.getDataPedido());
        pedidoExistente.setStatusPedido(pedido.getStatusPedido());
        pedidoExistente.setTotal(pedido.getTotal());
        pedidoExistente.setObservacoes(pedido.getObservacoes());
        pedidoExistente.setEstado(pedido.getEstado());
        pedidoExistente.setCep(pedido.getCep());
        pedidoExistente.setCidade(pedido.getCidade());
        pedidoExistente.setBairro(pedido.getBairro());
        pedidoExistente.setComplemento(pedido.getComplemento());
        pedidoExistente.setNumero(pedido.getNumero());
        pedidoExistente.setRua(pedido.getRua());

        return pedidoRepository.save(pedidoExistente); // Salva as alterações
    }

    /**
     * Este método deleta um pedido por ID.
     * 
     * @param id ID do pedido a ser deletado.
     */
    public void deletePedido(Long id) {
        PedidoModel pedido = getPedidoById(id); // Busca o pedido para garantir que existe
        pedidoRepository.delete(pedido); // Deleta o pedido
    }
}
