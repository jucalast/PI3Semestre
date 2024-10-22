package com.app.controller;

/* 
* Importa a classe PagCartaoModel para interagir com a entidade
* import com.app.model.PagCartaoModel;
* 
* Importa a classe PagCartaoService para acessar a lógica de negócios
* import com.app.service.PagCartaoService;
* 
* Importa as anotações do Spring para criar um controlador REST
* import org.springframework.beans.factory.annotation.Autowired;
* import org.springframework.http.HttpStatus;
* import org.springframework.http.ResponseEntity;
* import org.springframework.web.bind.annotation.*;
* 
* Importa a classe ResponseStatusException para tratamento de exceções
* import org.springframework.web.server.ResponseStatusException;
*/
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.model.PagCartaoModel;
import com.app.service.PagCartaoService;

/**
 * Controlador para a entidade 'PagCartaoModel'.
 * Esta classe expõe endpoints REST para permitir operações de
 * criação, leitura, atualização e exclusão de pagamentos realizados
 * com cartão de crédito.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
 */
@RestController
@RequestMapping("/api/pagCartao")
public class PagCartaoController {

    private final PagCartaoService pagCartaoService;

    /**
     * Construtor que injeta o serviço de pagamentos com cartão.
     * 
     * @param pagCartaoService O serviço a ser injetado.
     */
    @Autowired
    public PagCartaoController(PagCartaoService pagCartaoService) {
        this.pagCartaoService = pagCartaoService;
    }

    /**
     * Cria um novo pagamento com cartão.
     * 
     * @param pagCartaoModel O modelo do pagamento a ser criado.
     * @return ResponseEntity contendo o pagamento criado e o status HTTP.
     */
    @PostMapping
    public ResponseEntity<PagCartaoModel> criarPagamento(@RequestBody PagCartaoModel pagCartaoModel) {
        PagCartaoModel novoPagamento = pagCartaoService.salvar(pagCartaoModel);
        return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
    }

    /**
     * Busca um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento a ser buscado.
     * @return ResponseEntity contendo o pagamento encontrado e o status HTTP.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagCartaoModel> buscarPagamento(@PathVariable Long id) {
        PagCartaoModel pagamento = pagCartaoService.buscarPorId(id);
        return new ResponseEntity<>(pagamento, HttpStatus.OK);
    }

    /**
     * Lista todos os pagamentos com cartão.
     * 
     * @return ResponseEntity contendo a lista de pagamentos e o status HTTP.
     */
    @GetMapping
    public ResponseEntity<List<PagCartaoModel>> listarPagamentos() {
        List<PagCartaoModel> pagamentos = pagCartaoService.listarTodos();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    /**
     * Atualiza um pagamento com cartão existente.
     * 
     * @param id O ID do pagamento a ser atualizado.
     * @param pagCartaoModel O modelo atualizado do pagamento.
     * @return ResponseEntity contendo o pagamento atualizado e o status HTTP.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagCartaoModel> atualizarPagamento(
            @PathVariable Long id, @RequestBody PagCartaoModel pagCartaoModel) {
        PagCartaoModel pagamentoAtualizado = pagCartaoService.atualizar(id, pagCartaoModel);
        return new ResponseEntity<>(pagamentoAtualizado, HttpStatus.OK);
    }

    /**
     * Remove um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento a ser removido.
     * @return ResponseEntity com o status HTTP.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPagamento(@PathVariable Long id) {
        pagCartaoService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
