package com.app.controller;

import com.app.exceptions.ProdutoNotFoundException;
import com.app.model.*;
import com.app.model.pagamento.MetodoPagamentoModel;
import com.app.model.pagamento.PagamentoModel;
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
     *
     * @param pedidoData Dados estruturados do pedido incluindo usuário, produtos e detalhes de pagamento.
     * @param request    Objeto HttpServletRequest para acesso à sessão e autenticação do usuário.
     * @return Retorna uma resposta ResponseEntity contendo o pedido criado ou uma mensagem de erro adequada.
     */
    @PostMapping("/api/pedidos")
    public ResponseEntity<?> criarPedido(@RequestBody Map<String, Object> pedidoData, HttpServletRequest request) {
        try {
            System.out.println("Dados recebidos: " + pedidoData);
            Long userId = (Long) request.getSession().getAttribute("userId");
            UserModel usuario = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));

            // Configura o pedido
            PedidoModel pedido = new PedidoModel();
            LocalDateTime now = LocalDateTime.now();
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

            // Processa os produtos do pedido
            List<Map<String, Object>> produtos = (List<Map<String, Object>>) pedidoData.get("produtos");
            if (produtos == null || produtos.isEmpty()) {
                throw new IllegalArgumentException("A lista de produtos não pode estar vazia.");
            }


            List<ProdutoPedidoModel> produtosPedido = produtos.stream().map(produtoData -> {
                ProdutoPedidoModel produtoPedido = new ProdutoPedidoModel();

                // Conversões explícitas
                Long produtoId = Long.valueOf(produtoData.get("produtoId").toString());
                Integer quantidade = Integer.valueOf(produtoData.get("quantidade").toString());

                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + produtoId + " não encontrado."));
                produtoPedido.setProduto(produto);

                produtoPedido.setQuantidade(quantidade);
                BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
                produtoPedido.setSubtotal(subtotal);

                return produtoPedido;
            }).toList();

            BigDecimal totalPedido = produtosPedido.stream()
                    .map(ProdutoPedidoModel::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            pedido.setTotal(totalPedido);

            // Processa o pagamento usando o serviço
            Map<String, Object> pagamentoData = (Map<String, Object>) pedidoData.get("pagamento");
            if (pagamentoData == null || pagamentoData.isEmpty()) {
                throw new IllegalArgumentException("Os dados de pagamento não podem estar vazios.");
            }

            MetodoPagamentoModel metodoPagamento = pedidoService.processarPagamento(pagamentoData, totalPedido, usuario);

            PagamentoModel pagamento = new PagamentoModel();            pagamento.setMetodoPagamento(metodoPagamento);
            pagamento.setValorTotal(totalPedido);
            pagamento.setDataPagamento(LocalDateTime.now());
            pagamento.setStatus((String) pagamentoData.get("status"));

            // Cria o pedido
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
