package com.app.controller;

import com.app.model.PagamentoModel;
import com.app.model.PagCartaoModel;
import com.app.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsável por gerenciar as requisições relacionadas a pagamentos,
 * fornecendo endpoints para realizar operações CRUD na entidade 'PagamentoModel'.
 */
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /**
     * Endpoint para salvar um novo pagamento. Se for um pagamento com cartão,
     * também salva os detalhes do cartão.
     *
     * @param pagamentoModel O modelo do pagamento enviado no corpo da requisição.
     * @param pagCartaoModel O modelo dos detalhes do cartão (se aplicável).
     * @return ResponseEntity com o pagamento salvo.
     */
    @PostMapping
    public ResponseEntity<PagamentoModel> salvarPagamento(
            @RequestBody PagamentoModel pagamentoModel,
            @RequestBody(required = false) Optional<PagCartaoModel> pagCartaoModel) {
        PagamentoModel pagamentoSalvo = pagamentoService.salvarPagamentoComCartao(pagamentoModel, pagCartaoModel);
        return new ResponseEntity<>(pagamentoSalvo, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar um pagamento pelo ID.
     *
     * @param id O ID do pagamento a ser buscado.
     * @return ResponseEntity com o pagamento encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoModel> buscarPagamentoPorId(@PathVariable Long id) {
        PagamentoModel pagamentoModel = pagamentoService.buscarPorId(id);
        return new ResponseEntity<>(pagamentoModel, HttpStatus.OK);
    }

    /**
     * Endpoint para listar todos os pagamentos.
     *
     * @return ResponseEntity com a lista de todos os pagamentos.
     */
    @GetMapping
    public ResponseEntity<List<PagamentoModel>> listarPagamentos() {
        List<PagamentoModel> pagamentos = pagamentoService.listarTodos();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    /**
     * Endpoint para atualizar um pagamento existente pelo ID.
     *
     * @param id O ID do pagamento a ser atualizado.
     * @param pagamentoModel O modelo atualizado do pagamento enviado no corpo da requisição.
     * @return ResponseEntity com o pagamento atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoModel> atualizarPagamento(
            @PathVariable Long id,
            @RequestBody PagamentoModel pagamentoModel) {
        PagamentoModel pagamentoAtualizado = pagamentoService.atualizar(id, pagamentoModel);
        return new ResponseEntity<>(pagamentoAtualizado, HttpStatus.OK);
    }

    /**
     * Endpoint para remover um pagamento pelo ID.
     *
     * @param id O ID do pagamento a ser removido.
     * @return ResponseEntity sem conteúdo, indicando que a remoção foi bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPagamento(@PathVariable Long id) {
        pagamentoService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
