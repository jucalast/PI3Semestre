package com.app.controller;

import com.app.exceptions.ProdutoNotFoundException;
import com.app.model.*;
import com.app.repository.CartaoRepository;
import com.app.repository.ProdutoRepository;
import com.app.repository.UserRepository;
import com.app.service.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gerenciamento de pedidos dentro da aplicação.
 * Provê funcionalidades para a criação de pedidos através de uma API REST.
 */
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CartaoRepository pagCartaoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint POST para criar um novo pedido com base nos dados enviados no corpo da requisição.
     * Este método manipula a criação completa do pedido, incluindo validação de usuário, produtos e registro de pagamento.
     *
     * @param pedidoData Dados estruturados do pedido incluindo usuário, produtos e detalhes de pagamento.
     * @param request    Objeto HttpServletRequest para acesso à sessão e autenticação do usuário.
     * @return Retorna uma resposta ResponseEntity contendo o pedido criado ou uma mensagem de erro adequada.
     */
    @PostMapping("/api/pedidos")
    public ResponseEntity<?> criarPedido(@RequestBody Map<String, Object> pedidoData, HttpServletRequest request) {
        try {
            // Recupera o usuário autenticado
            Long userId = (Long) request.getSession().getAttribute("userId");
            UserModel usuario = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

            // Valida os dados do pedido
            if (pedidoData == null || pedidoData.isEmpty()) {
                throw new IllegalArgumentException("Dados do pedido não podem estar vazios.");
            }

            // Cria o modelo de pedido
            PedidoModel pedido = new PedidoModel();
            LocalDateTime now = LocalDateTime.now(); // Data e hora atuais
            pedido.setDataPedido(now);
            pedido.setStatusPedido((String) pedidoData.get("statusPedido"));
            pedido.setObservacoes((String) pedidoData.get("observacoes"));
            pedido.setEstado((String) pedidoData.get("estado"));
            pedido.setCep((String) pedidoData.get("cep"));
            pedido.setCidade((String) pedidoData.get("cidade"));
            pedido.setBairro((String) pedidoData.get("bairro"));
            pedido.setNumero((Integer) pedidoData.get("numero"));
            pedido.setRua((String) pedidoData.get("rua"));
            pedido.setComplemento((String) pedidoData.get("complemento"));

            // Processa os produtos
            List<Map<String, Object>> produtos = (List<Map<String, Object>>) pedidoData.get("produtos");
            if (produtos == null || produtos.isEmpty()) {
                throw new IllegalArgumentException("A lista de produtos não pode estar vazia.");
            }

            List<ProdutoPedidoModel> produtosPedido = produtos.stream().map(produtoData -> {
                ProdutoPedidoModel produtoPedido = new ProdutoPedidoModel();
                Long produtoId = ((Number) produtoData.get("produtoId")).longValue();
                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + produtoId + " não encontrado."));
                produtoPedido.setProduto(produto);

                Integer quantidade = (Integer) produtoData.get("quantidade");
                produtoPedido.setQuantidade(quantidade);

                // Calcula o subtotal
                BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
                produtoPedido.setSubtotal(subtotal);

                return produtoPedido;
            }).toList();

            // Calcula o total do pedido
            BigDecimal totalPedido = produtosPedido.stream()
                    .map(ProdutoPedidoModel::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            pedido.setTotal(totalPedido);

            // Processa os dados de pagamento
            Map<String, Object> pagamentoData = (Map<String, Object>) pedidoData.get("pagamento");
            if (pagamentoData == null || pagamentoData.isEmpty()) {
                throw new IllegalArgumentException("Os dados de pagamento não podem estar vazios.");
            }

            PagamentoModel pagamento = new PagamentoModel();
            pagamento.setDataPagamento(now); // Define a data atual
            pagamento.setMetodoPagamento((String) pagamentoData.get("metodoPagamento"));
            pagamento.setStatus((String) pagamentoData.get("status"));
            pagamento.setValorTotal(totalPedido); // Define o valor total igual ao total do pedido

            // Processa os dados do cartão, se fornecido
            if (pagamentoData.containsKey("cartao")) {
                Map<String, Object> cartaoData = (Map<String, Object>) pagamentoData.get("cartao");
                String numeroCartao = (String) cartaoData.get("numeroCartao");

                // Verifica se o cartão já existe no banco
                Optional<CartaoModel> cartaoExistente = pagCartaoRepository.findByNumeroCartao(numeroCartao);

                if (cartaoExistente.isPresent()) {
                    // Reutiliza o cartão existente
                    pagamento.setCartao(cartaoExistente.get());
                } else {
                    // Cria um novo cartão, pois ele não existe
                    CartaoModel novoCartao = new CartaoModel();
                    novoCartao.setNumeroCartao(numeroCartao);
                    novoCartao.setNomeTitular((String) cartaoData.get("nomeTitular"));
                    novoCartao.setValidade((String) cartaoData.get("validade"));
                    novoCartao.setBandeira((String) cartaoData.get("bandeira"));
                    novoCartao.setCpfTitular((String) cartaoData.get("cpfTitular"));
                    novoCartao.setUser(usuario); // Associa o cartão ao usuário autenticado

                    // Salva o novo cartão
                    pagCartaoRepository.save(novoCartao);
                    pagamento.setCartao(novoCartao);
                }
            }

            // Salva o pedido usando o serviço
            PedidoModel pedidoCriado = pedidoService.criarPedido(pedido, produtosPedido, pagamento, userId);

            return ResponseEntity.ok(pedidoCriado);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar pedido: " + e.getMessage());
        }
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<?> handleProdutoNotFoundException(ProdutoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
