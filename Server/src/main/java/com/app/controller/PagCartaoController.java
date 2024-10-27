package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.PagCartaoModel;
import com.app.service.PagCartaoService;

/**
 * Classe de controle responsável por expor APIs relacionadas à entidade PagCartaoModel.
 * Esta classe fornece endpoints para operações de CRUD e consultas de pagamentos com cartão.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 * 
 * @see com.app.service.PagCartaoService
 */
@RestController
@RequestMapping("/api/pagamentos/cartao")
public class PagCartaoController {

    private final PagCartaoService pagCartaoService;

    /**
     * Construtor da classe PagCartaoController que injeta o serviço de pagamentos com cartão.
     * 
     * @param pagCartaoService O serviço para operações de pagamentos com cartão.
     */
    @Autowired
    public PagCartaoController(PagCartaoService pagCartaoService) {
        this.pagCartaoService = pagCartaoService;
    }

    /**
     * Endpoint para criar um novo pagamento com cartão.
     * 
     * @param pagCartao Objeto que representa o pagamento com cartão a ser criado.
     * @return Resposta com o pagamento criado e status 201 Created.
     */
    @PostMapping
    public ResponseEntity<PagCartaoModel> criarPagCartao(@RequestBody PagCartaoModel pagCartao) {
        PagCartaoModel novoPagCartao = pagCartaoService.salvarPagCartao(pagCartao);
        return new ResponseEntity<>(novoPagCartao, HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar todos os pagamentos com cartão.
     * 
     * @return Lista de pagamentos com cartão.
     */
    @GetMapping
    public ResponseEntity<List<PagCartaoModel>> listarPagCartao() {
        List<PagCartaoModel> pagamentos = pagCartaoService.listarPagCartao();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    /**
     * Endpoint para obter um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento com cartão a ser buscado.
     * @return Resposta com o pagamento encontrado e status 200 OK, ou 404 Not Found se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagCartaoModel> obterPagCartaoPorId(@PathVariable Long id) {
        Optional<PagCartaoModel> pagCartao = pagCartaoService.obterPagCartaoPorId(id);
        return pagCartao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para atualizar um pagamento com cartão existente.
     * 
     * @param id O ID do pagamento com cartão a ser atualizado.
     * @param pagCartao Os novos dados do pagamento com cartão.
     * @return Resposta com o pagamento atualizado e status 200 OK, ou 404 Not Found se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagCartaoModel> atualizarPagCartao(@PathVariable Long id, @RequestBody PagCartaoModel pagCartao) {
        Optional<PagCartaoModel> pagCartaoAtualizado = pagCartaoService.atualizarPagCartao(id, pagCartao);
        return pagCartaoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para excluir um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento com cartão a ser excluído.
     * @return Resposta com status 204 No Content se excluído com sucesso, ou 404 Not Found se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagCartao(@PathVariable Long id) {
        if (pagCartaoService.obterPagCartaoPorId(id).isPresent()) {
            pagCartaoService.deletarPagCartao(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para encontrar um pagamento com cartão pelo ID do pagamento associado.
     * 
     * @param pagamentoId ID do pagamento associado.
     * @return Resposta com o pagamento encontrado ou 404 Not Found se não encontrado.
     */
    @GetMapping("/por-pagamento/{pagamentoId}")
    public ResponseEntity<PagCartaoModel> obterPagCartaoPorPagamentoId(@PathVariable Long pagamentoId) {
        Optional<PagCartaoModel> pagCartao = pagCartaoService.obterPagCartaoPorPagamentoId(pagamentoId);
        return pagCartao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para contar pagamentos com cartão por bandeira.
     * 
     * @param bandeiraCartao A bandeira do cartão.
     * @return Resposta com a contagem de registros encontrados.
     */
    @GetMapping("/contar/bandeira/{bandeiraCartao}")
    public ResponseEntity<Long> contarPorBandeiraCartao(@PathVariable String bandeiraCartao) {
        long count = pagCartaoService.contarPorBandeiraCartao(bandeiraCartao);
        return ResponseEntity.ok(count);
    }

    /**
     * Endpoint para listar pagamentos com cartão por número de parcelas.
     * 
     * @param parcelas Número de parcelas.
     * @return Lista de pagamentos com cartão.
     */
    @GetMapping("/por-parcelas/{parcelas}")
    public ResponseEntity<List<PagCartaoModel>> listarPorParcelas(@PathVariable Integer parcelas) {
        List<PagCartaoModel> pagamentos = pagCartaoService.listarPorParcelas(parcelas);
        return ResponseEntity.ok(pagamentos);
    }

    /**
     * Endpoint para obter um pagamento com cartão pelo CPF do titular.
     * 
     * @param cpf CPF do titular.
     * @return Resposta com o pagamento encontrado ou 404 Not Found se não encontrado.
     */
    @GetMapping("/por-cpf/{cpf}")
    public ResponseEntity<PagCartaoModel> obterPagCartaoPorCpf(@PathVariable String cpf) {
        Optional<PagCartaoModel> pagCartao = pagCartaoService.obterPagCartaoPorCpf(cpf);
        return pagCartao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
