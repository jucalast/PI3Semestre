package com.app.controller;

import com.app.DTO.PedidoDTO; // Importando o DTO correto
import com.app.model.PedidoModel;
import com.app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoModel> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> getPedidoById(@PathVariable Long id) {
        PedidoModel pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoModel> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoModel createdPedido = pedidoService.criarPedidoComProdutos(pedidoDTO);
        return ResponseEntity.status(201).body(createdPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> updatePedido(@PathVariable Long id, @RequestBody PedidoModel pedido) {
        PedidoModel updatedPedido = pedidoService.updatePedido(id, pedido);
        return ResponseEntity.ok(updatedPedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
