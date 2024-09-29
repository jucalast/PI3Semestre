package com.app.controller;

import com.app.model.Pagamento;
import com.app.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para a entidade Pagamento.
 * Manipula requisições relacionadas a pagamentos.
 */
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /**
     * Cria um novo pagamento.
     *
     * @param pagamento objeto Pagamento a ser criado
     * @return ResponseEntity com o pagamento criado e o status HTTP
     */
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento novoPagamento = pagamentoService.salvarPagamento(pagamento);
        return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
    }

    /**
     * Obtém todos os pagamentos.
     *
     * @return ResponseEntity com a lista de pagamentos e o status HTTP
     */
    @GetMapping
    public ResponseEntity<List<Pagamento>> listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.listarPagamentos();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    /**
     * Obtém um pagamento específico pelo ID.
     *
     * @param id ID do pagamento a ser buscado
     * @return ResponseEntity com o pagamento encontrado e o status HTTP
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> obterPagamentoPorId(@PathVariable Integer id) {
        Pagamento pagamento = pagamentoService.obterPagamentoPorId(id);
        return pagamento != null ? new ResponseEntity<>(pagamento, HttpStatus.OK)
                                 : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Atualiza um pagamento existente.
     *
     * @param id        ID do pagamento a ser atualizado
     * @param pagamento objeto Pagamento com os novos dados
     * @return ResponseEntity com o pagamento atualizado e o status HTTP
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Integer id,
                                                       @RequestBody Pagamento pagamento) {
        Pagamento pagamentoAtualizado = pagamentoService.atualizarPagamento(id, pagamento);
        return pagamentoAtualizado != null ? new ResponseEntity<>(pagamentoAtualizado, HttpStatus.OK)
                                            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Remove um pagamento pelo ID.
     *
     * @param id ID do pagamento a ser removido
     * @return ResponseEntity com o status HTTP
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPagamento(@PathVariable Integer id) {
        if (pagamentoService.removerPagamento(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
