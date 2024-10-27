package com.app.controller;

import com.app.DTO.PagamentoCompletoDTO;
import com.app.model.PagamentoModel;
import com.app.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    // Endpoint para criar um pagamento completo
    @PostMapping("/completo")
    public ResponseEntity<PagamentoModel> criarPagamentoCompleto(@Valid @RequestBody PagamentoCompletoDTO pagamentoDTO) {
        PagamentoModel novoPagamento = pagamentoService.criarPagamentoCompleto(pagamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
    }

    // Endpoint para salvar ou atualizar um pagamento
    @PostMapping
    public ResponseEntity<PagamentoModel> salvarPagamento(@RequestBody PagamentoModel pagamento) {
        PagamentoModel pagamentoSalvo = pagamentoService.salvarPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoSalvo);
    }

    // Endpoint para buscar um pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoModel> buscarPagamentoPorId(@PathVariable Integer id) {
        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorId(id);
        return pagamento.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para listar todos os pagamentos
    @GetMapping
    public ResponseEntity<List<PagamentoModel>> listarTodosPagamentos() {
        List<PagamentoModel> pagamentos = pagamentoService.listarTodosPagamentos();
        return ResponseEntity.ok(pagamentos);
    }

    // Endpoint para listar pagamentos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorStatus(@PathVariable PagamentoModel.StatusPagamento status) {
        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorStatus(status);
        return ResponseEntity.ok(pagamentos);
    }

    // Endpoint para listar pagamentos por ID do pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorPedidoId(@PathVariable Integer pedidoId) {
        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorPedidoId(pedidoId);
        return ResponseEntity.ok(pagamentos);
    }

    // Endpoint para listar pagamentos por ID do cupom
    @GetMapping("/cupom/{cupomId}")
    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorCupomId(@PathVariable Integer cupomId) {
        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorCupomId(cupomId);
        return ResponseEntity.ok(pagamentos);
    }

    // Endpoint para excluir um pagamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagamento(@PathVariable Integer id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
