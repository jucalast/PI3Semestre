package com.app.controller;

/**
 * Bibliotecas utilizadas:
 * 
 * java.util.List - Permite trabalhar com listas de objetos.
 * java.util.Optional - Fornece uma maneira de lidar com valores que podem ser nulos.
 * org.springframework.beans.factory.annotation.Autowired - Para injeção de dependência.
 * org.springframework.http.ResponseEntity - Representa a resposta HTTP.
 * org.springframework.web.bind.annotation.GetMapping - Mapeia requisições HTTP GET.
 * org.springframework.web.bind.annotation.PatchMapping - Mapeia requisições HTTP PATCH.
 * org.springframework.web.bind.annotation.PathVariable - Extrai valores da URL.
 * org.springframework.web.bind.annotation.PostMapping - Mapeia requisições HTTP POST.
 * org.springframework.web.bind.annotation.PutMapping - Mapeia requisições HTTP PUT.
 * org.springframework.web.bind.annotation.RequestBody - Mapeia o corpo da requisição.
 * org.springframework.web.bind.annotation.RequestMapping - Define a URL base do controlador.
 * org.springframework.web.bind.annotation.RestController - Indica que a classe é um controlador REST.
 * com.app.model.PedidoModel - Representa os dados de um pedido.
 * com.app.service.PedidoService - Contém a lógica de negócios para os pedidos.
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.PedidoModel;
import com.app.service.PedidoService;

/**
 * Controlador REST para gerenciar pedidos.
 * Esta classe lida com as solicitações HTTP relacionadas aos pedidos,
 * utilizando o PedidoService para executar a lógica de negócios.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Construtor da classe PedidoController.
     * Este construtor recebe o PedidoService, que é injetado automaticamente pelo Spring.
     *
     * @param pedidoService O serviço que gerencia a lógica de negócios dos pedidos.
     */
    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Cria um novo pedido.
     *
     * @param pedido o pedido a ser criado; deve ser enviado no corpo da requisição como JSON.
     * @return ResponseEntity contendo o pedido criado e um status HTTP 200 (OK).
     */
    @PostMapping
    public ResponseEntity<PedidoModel> criarPedido(@RequestBody PedidoModel pedido) {
        PedidoModel novoPedido = pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    /**
     * Lista todos os pedidos.
     *
     * @return ResponseEntity contendo uma lista de pedidos e um status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<PedidoModel>> listarPedidos() {
        List<PedidoModel> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Obtém um pedido por ID.
     *
     * @param id o ID do pedido a ser obtido; deve ser passado na URL.
     * @return ResponseEntity contendo o pedido encontrado ou um status HTTP 404 (Not Found) se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> obterPedidoPorId(@PathVariable Integer id) {
        Optional<PedidoModel> pedido = pedidoService.obterPedidoPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Atualiza um pedido existente.
     *
     * @param id     o ID do pedido a ser atualizado; deve ser passado na URL.
     * @param pedido os novos dados do pedido; devem ser enviados no corpo da requisição como JSON.
     * @return ResponseEntity contendo o pedido atualizado ou um status HTTP 404 (Not Found) se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> atualizarPedido(@PathVariable Integer id, @RequestBody PedidoModel pedido) {
        Optional<PedidoModel> pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
        return pedidoAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Atualiza o status de um pedido.
     *
     * @param id     o ID do pedido a ser atualizado; deve ser passado na URL.
     * @param status o novo status do pedido; deve ser enviado no corpo da requisição.
     * @return ResponseEntity contendo o pedido atualizado ou um status HTTP 404 (Not Found) se não encontrado.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoModel> atualizarStatus(@PathVariable Integer id, @RequestBody Integer status) {
        Optional<PedidoModel> pedidoAtualizado = pedidoService.atualizarStatus(id, status);
        return pedidoAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }}