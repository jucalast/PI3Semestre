package com.app.controller;

import com.app.DTO.PedidoDTO; // Importando o DTO correto
import com.app.model.PedidoModel;
import com.app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as requisições relacionadas aos pedidos.
 * Este controlador fornece endpoints para criação, consulta, atualização e remoção de pedidos.
 * 
 * Bibliotecas utilizadas:
 * 
 * - com.app.DTO.PedidoDTO: Classe que encapsula os dados do pedido a serem transferidos entre a camada de apresentação e a camada de serviço.
 * - com.app.model.PedidoModel: Classe que representa o modelo de dados de um pedido.
 * - com.app.service.PedidoService: Classe de serviço que contém a lógica de negócios relacionada aos pedidos.
 * - org.springframework.beans.factory.annotation.Autowired: Permite a injeção de dependência em classes Spring.
 * - org.springframework.http.ResponseEntity: Classe que encapsula a resposta HTTP, incluindo o corpo e os cabeçalhos.
 * - org.springframework.web.bind.annotation.*: Contém anotações para definir controladores e mapear requisições HTTP para métodos (como @RestController, @RequestMapping, @GetMapping, @PostMapping, etc.).
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Endpoint para listar todos os pedidos.
     *
     * @return Uma lista de todos os pedidos.
     */
    @GetMapping
    public List<PedidoModel> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    /**
     * Endpoint para buscar um pedido pelo ID.
     *
     * @param id O ID do pedido a ser buscado.
     * @return Resposta com o pedido encontrado ou status 404 (Not Found) se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> getPedidoById(@PathVariable Long id) {
        PedidoModel pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Endpoint para criar um novo pedido.
     *
     * @param pedidoDTO O objeto DTO que contém os dados do pedido a ser criado.
     * @return Resposta com o pedido criado e status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<PedidoModel> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoModel createdPedido = pedidoService.criarPedidoComProdutos(pedidoDTO);
        return ResponseEntity.status(201).body(createdPedido);
    }

    /**
     * Endpoint para atualizar um pedido existente.
     *
     * @param id O ID do pedido a ser atualizado.
     * @param pedido O objeto PedidoModel com os novos dados do pedido.
     * @return Resposta com o pedido atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> updatePedido(@PathVariable Long id, @RequestBody PedidoModel pedido) {
        PedidoModel updatedPedido = pedidoService.updatePedido(id, pedido);
        return ResponseEntity.ok(updatedPedido);
    }

    /**
     * Endpoint para excluir um pedido pelo ID.
     *
     * @param id O ID do pedido a ser excluído.
     * @return Resposta com status 204 (No Content) se a exclusão for bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
