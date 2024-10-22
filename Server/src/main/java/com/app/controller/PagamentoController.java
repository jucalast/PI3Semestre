package com.app.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;
import com.app.service.PagamentoService;

/**
 * Controlador responsável pelas operações relacionadas à entidade 'pagamentos'.
 * Este controlador fornece endpoints para manipular pagamentos, como buscar,
 * criar, atualizar e excluir.
 * 
 * As respostas são padronizadas com a classe ResponseEntity, que permite
 * controlar o status HTTP das respostas.
 * 
 * @author Kairo Chácara
 * @version 1.1
 * @since 2024-10-21
 */
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    /**
     * Injeção do serviço de pagamentos responsável pelas regras de negócio e operações
     * de persistência.
     */
    @Autowired
    private PagamentoService pagamentoService;

    /**
     * Endpoint para criar um novo pagamento. Caso o pagamento seja feito por cartão,
     * também registra as informações do cartão.
     * 
     * @param pagamento Objeto PagamentoModel com as informações do pagamento a ser criado.
     * @param pagCartao (Opcional) Objeto PagCartaoModel com as informações do pagamento por cartão.
     * @return ResponseEntity contendo o pagamento criado e status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<PagamentoModel> criarPagamento(@RequestBody PagamentoModel pagamento, 
            @RequestBody(required = false) PagCartaoModel pagCartao) {
        PagamentoModel pagamentoCriado = pagamentoService.salvarPagamento(pagamento);

        // Verifica se o pagamento foi feito por cartão
        if (pagamento.getMetodoPagamento() == 1 && pagCartao != null) { 
            pagCartao.setPagamentoId(pagamentoCriado.getId().intValue()); // Associa o pagamento ao cartão
            pagamentoService.salvarPagamentoCartao(pagCartao); // Salva os dados do cartão
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
    } 

    /**
     * Endpoint para buscar um pagamento pelo seu ID.
     * 
     * @param id ID do pagamento a ser buscado.
     * @return ResponseEntity contendo o pagamento encontrado ou status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoModel> buscarPagamentoPorId(@PathVariable Long id) {
        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorId(id);
        return pagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Endpoint para atualizar um pagamento existente.
     * 
     * @param id        ID do pagamento a ser atualizado.
     * @param pagamento Objeto PagamentoModel com as informações atualizadas.
     * @return ResponseEntity com o pagamento atualizado ou status 404 (Not Found) se o pagamento não for encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoModel> atualizarPagamento(@PathVariable Long id, @RequestBody PagamentoModel pagamento) {
        Optional<PagamentoModel> pagamentoExistente = pagamentoService.buscarPagamentoPorId(id);
        if (pagamentoExistente.isPresent()) {
            pagamento.setId(id);  // Garante que o ID não será alterado
            PagamentoModel pagamentoAtualizado = pagamentoService.salvarPagamento(pagamento);
            return ResponseEntity.ok(pagamentoAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint para excluir um pagamento pelo seu ID.
     * 
     * @param id ID do pagamento a ser excluído.
     * @return ResponseEntity com status 204 (No Content) se o pagamento for excluído ou 404 (Not Found) se não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagamento(@PathVariable Long id) {
        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorId(id);
        if (pagamento.isPresent()) {
            pagamentoService.excluirPagamento(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint para buscar um pagamento pelo seu Transaction ID.
     * 
     * @param transactionId Transaction ID do pagamento a ser buscado.
     * @return ResponseEntity contendo o pagamento encontrado ou status 404 (Not Found).
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PagamentoModel> buscarPorTransactionId(@PathVariable String transactionId) {
        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorTransactionId(transactionId);
        return pagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Endpoint para aplicar um desconto ao pagamento.
     * 
     * @param id       ID do pagamento ao qual o desconto será aplicado.
     * @param desconto Valor do desconto a ser aplicado.
     * @return ResponseEntity contendo o pagamento atualizado com o desconto ou status 404 (Not Found).
     */
    @PutMapping("/{id}/desconto")
    public ResponseEntity<PagamentoModel> aplicarDesconto(@PathVariable Long id, @RequestParam BigDecimal desconto) {
        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorId(id);
        if (pagamento.isPresent()) {
            PagamentoModel pagamentoComDesconto = pagamentoService.aplicarDesconto(pagamento.get(), desconto);
            return ResponseEntity.ok(pagamentoComDesconto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
