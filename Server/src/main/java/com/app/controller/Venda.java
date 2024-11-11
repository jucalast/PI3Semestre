package com.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.PedidoDTO;
import com.app.DTO.ProdutoPedidoDTO;
import com.app.model.PedidoModel;
import com.app.model.UserModel;
import com.app.service.CarrinhoService;
import com.app.service.PedidoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/venda")
public class Venda {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PedidoService pedidoService;

    /**
     * Este método recebe os itens do carrinho do usuário autenticado,
     * converte-os em um pedido e chama o serviço para salvar o pedido.
     * 
     * @param request Requisição HTTP contendo a sessão com o usuário autenticado.
     * @return ResponseEntity com o pedido criado ou erro caso o usuário não esteja autenticado.
     */
    @PostMapping("/conversao")
    public ResponseEntity<?> moverParaPedido(HttpServletRequest request) {
        // Obter o usuário autenticado
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não autenticado.");
        }

        Long userId = authenticatedUser.getId();

        // Obter os itens do carrinho para o usuário autenticado
        List<Map<String, Object>> carrinhoItems = carrinhoService.getProductsOnUserCart(userId);

        // Converter itens do carrinho para PedidoDTO
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setUsuarioId(authenticatedUser); // Passar o objeto UserModel completo
        pedidoDTO.setDataPedido(LocalDateTime.now().toString()); // Aqui você pode considerar usar LocalDateTime, caso necessário
        pedidoDTO.setStatusPedido(0); // Definir um status inicial (por exemplo, 0 - Pendente)

        List<ProdutoPedidoDTO> produtosDTO = new ArrayList<>();
        for (Map<String, Object> item : carrinhoItems) {
            ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO();
            produtoPedidoDTO.setProdutoId((Long) item.get("produtoId"));
            produtoPedidoDTO.setQuantidade((Integer) item.get("quantidade"));
            produtosDTO.add(produtoPedidoDTO);
        }
        pedidoDTO.setProdutos(produtosDTO);

        // Criar o pedido com produtos
        PedidoModel createdPedido = pedidoService.criarPedidoComProdutos(pedidoDTO);

        // Retornar o pedido criado como resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPedido);
    }

    /**
     * Este método recebe os dados de um único produto e cria um pedido diretamente.
     * 
     * @param request Requisição HTTP contendo a sessão com o usuário autenticado.
     * @param produtoPedidoDTO Dados do produto a ser inserido no pedido.
     * @return ResponseEntity com o pedido criado ou erro caso o usuário não esteja autenticado.
     */
    @PostMapping("/pedido-unico")
    public ResponseEntity<?> criarPedidoUnico(HttpServletRequest request, @RequestBody ProdutoPedidoDTO produtoPedidoDTO) {
        // Obter o usuário autenticado
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não autenticado.");
        }

        // Criar o PedidoDTO
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setUsuarioId(authenticatedUser); // Associando o usuário ao pedido
        pedidoDTO.setDataPedido(LocalDateTime.now().toString()); // Definir a data do pedido
        pedidoDTO.setStatusPedido(0); // Status do pedido (0 - Pendente)

        // Adicionar o produto único à lista de produtos do pedido
        produtoPedidoDTO.setProdutoId(produtoPedidoDTO.getProdutoId());
        produtoPedidoDTO.setQuantidade(produtoPedidoDTO.getQuantidade());
        pedidoDTO.setProdutos(Collections.singletonList(produtoPedidoDTO)); // Usando um único produto

        // Criar o pedido no banco de dados
        PedidoModel createdPedido = pedidoService.criarPedidoComProdutos(pedidoDTO);

        // Retornar o pedido criado como resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPedido);
    }
}

