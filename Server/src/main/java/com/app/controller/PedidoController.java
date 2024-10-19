package com.app.controller;

import com.app.model.PedidoModel;
import com.app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Endpoint para criar ou atualizar um pedido.
     *
     * @param pedidoModel Objeto do pedido a ser criado ou atualizado.
     * @return ResponseEntity com o pedido salvo ou atualizado e o status HTTP.
     */
    @PostMapping
    public ResponseEntity<PedidoModel> criarOuAtualizarPedido(@RequestBody PedidoModel pedidoModel) {
        PedidoModel pedidoSalvo = pedidoService.salvarOuAtualizarPedido(pedidoModel);
        return ResponseEntity.status(201).body(pedidoSalvo);
    }

    /**
     * Endpoint para obter todos os pedidos.
     *
     * @return Lista de pedidos.
     */
    @GetMapping
    public ResponseEntity<List<PedidoModel>> obterTodosOsPedidos() {
        List<PedidoModel> pedidos = pedidoService.listarTodosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Endpoint para obter um pedido específico pelo ID.
     *
     * @param id ID do pedido a ser obtido.
     * @return ResponseEntity com o pedido encontrado ou um erro se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> obterPedidoPorId(@PathVariable Integer id) {
        PedidoModel pedido = pedidoService.buscarPedidoPorId(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint para deletar um pedido pelo ID.
     *
     * @param id ID do pedido a ser deletado.
     * @return ResponseEntity com o status da operação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Integer id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para buscar pedidos por intervalo de datas.
     *
     * @param dataInicio Data de início do intervalo.
     * @param dataFim Data de fim do intervalo.
     * @return Lista de pedidos dentro do intervalo.
     */
    @GetMapping("/intervalo")
    public ResponseEntity<List<PedidoModel>> buscarPedidosPorIntervalo(
            @RequestParam String dataInicio, @RequestParam String dataFim) {
        List<PedidoModel> pedidos = pedidoService.buscarPedidosPorIntervaloDeDatas(dataInicio, dataFim);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Endpoint para buscar pedidos por status.
     *
     * @param statusPedido O status do pedido.
     * @return Lista de pedidos com o status especificado.
     */
    @GetMapping("/status/{statusPedido}")
    public ResponseEntity<List<PedidoModel>> buscarPedidosPorStatus(@PathVariable Integer statusPedido) {
        List<PedidoModel> pedidos = pedidoService.buscarPedidosPorStatus(statusPedido);
        return ResponseEntity.ok(pedidos);
    }
}
