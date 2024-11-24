package com.app.service;

import com.app.exceptions.ProdutoNotFoundException;
import com.app.model.*;
import com.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço que fornece operações de negócio para o gerenciamento de pedidos.
 * Este serviço encapsula toda a lógica necessária para a criação e manutenção de pedidos,
 * incluindo interações com o banco de dados através de repositórios.
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Cria um pedido e orquestra o salvamento de todos os componentes associados, incluindo produtos e pagamento.
     * Verifica a existência dos produtos, calcula o total, e associa o pagamento e os produtos ao pedido.
     *
     * @param pedido          Objeto PedidoModel contendo informações básicas do pedido.
     * @param produtosPedido  Lista de ProdutoPedidoModel com os produtos e quantidades.
     * @param pagamento       Objeto PagamentoModel com detalhes sobre o pagamento do pedido.
     * @param userId          ID do usuário que realiza o pedido.
     * @return O PedidoModel persistido com todas as associações necessárias.
     * @throws ProdutoNotFoundException Se algum dos produtos especificados não existir.
     */
    public PedidoModel criarPedido(PedidoModel pedido, List<ProdutoPedidoModel> produtosPedido, PagamentoModel pagamento, Long userId) {
        // Busca o usuário autenticado
        UserModel usuario = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));
        pedido.setUsuario(usuario);

        // Valida se todos os produtos existem antes de salvar
        BigDecimal total = BigDecimal.ZERO;
        for (ProdutoPedidoModel produtoPedido : produtosPedido) {
            Long produtoId = produtoPedido.getProduto().getId();
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + produtoId + " não encontrado."));
            produtoPedido.setProduto(produto);
            total = total.add(produtoPedido.getSubtotal());
        }
        pedido.setTotal(total);

        // Salva o pedido no banco
        PedidoModel pedidoSalvo = pedidoRepository.save(pedido);

        // Salva os produtos do pedido com referência ao pedido criado
        for (ProdutoPedidoModel produtoPedido : produtosPedido) {
            produtoPedido.setPedido(pedidoSalvo);
            produtoPedidoRepository.save(produtoPedido);
        }

        // Salva o cartão se fornecido
        if (pagamento.getCartao() != null) {
            cartaoRepository.save(pagamento.getCartao());
        }

        // Associa o pedido ao pagamento e salva
        pagamento.setPedido(pedidoSalvo);
        pagamentoRepository.save(pagamento);

        return pedidoSalvo;
    }
}
