package com.app.controller;

/**
 * Bibliotecas utilizadas:
 * 
 * - org.springframework.beans.factory.annotation.Autowired: Permite a injeção de dependência em classes Spring.
 * - org.springframework.http.HttpStatus: Representa os códigos de status HTTP, utilizados para construir respostas.
 * - org.springframework.http.ResponseEntity: Classe que encapsula a resposta HTTP, incluindo o corpo e os cabeçalhos.
 * - org.springframework.web.bind.annotation.*: Contém anotações para definir controladores e mapear requisições HTTP para métodos (como @RestController, @RequestMapping, @GetMapping, @PostMapping, etc.).
 * 
 * - com.app.model.MetodoPagamentoModel: Classe que representa o modelo de dados para métodos de pagamento.
 * - com.app.service.MetodoPagamentoService: Classe de serviço que contém a lógica de negócios relacionada aos métodos de pagamento.
 * - com.app.DTO.MetodoPagamentoNomeTaxaDTO: Classe de DTO que encapsula os dados de nome e taxa de um método de pagamento.
 */

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.MetodoPagamentoNomeTaxaDTO;
import com.app.model.MetodoPagamentoModel;
import com.app.service.MetodoPagamentoService;

/**
 * Controlador responsável por gerenciar as requisições relacionadas aos métodos de pagamento.
 * Este controlador fornece endpoints para criação, consulta, atualização e remoção de métodos de pagamento.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-14
 */
@RestController
@RequestMapping("/api/metodos-pagamento")
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    /**
     * Construtor do controlador que injeta o serviço de métodos de pagamento.
     *
     * @param metodoPagamentoService O serviço para gerenciar a lógica de negócios dos métodos de pagamento.
     */
    @Autowired
    public MetodoPagamentoController(MetodoPagamentoService metodoPagamentoService) {
        this.metodoPagamentoService = metodoPagamentoService;
    }

    /**
     * Endpoint para criar um novo método de pagamento.
     *
     * @param metodoPagamento O objeto MetodoPagamentoModel a ser criado.
     * @return Resposta com o método de pagamento criado e status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<MetodoPagamentoModel> criarMetodoPagamento(@RequestBody MetodoPagamentoModel metodoPagamento) {
        MetodoPagamentoModel novoMetodo = metodoPagamentoService.salvarMetodoPagamento(metodoPagamento);
        return new ResponseEntity<>(novoMetodo, HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar todos os métodos de pagamento.
     *
     * @return Uma lista de todos os métodos de pagamento e status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<MetodoPagamentoModel>> listarMetodosPagamento() {
        List<MetodoPagamentoModel> metodos = metodoPagamentoService.listarMetodosPagamento();
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    /**
     * Endpoint para obter um método de pagamento pelo ID.
     *
     * @param id O ID do método de pagamento a ser buscado.
     * @return Resposta com o método de pagamento encontrado ou status 404 (Not Found) se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagamentoModel> obterMetodoPagamentoPorId(@PathVariable Integer id) {
        Optional<MetodoPagamentoModel> metodoPagamento = metodoPagamentoService.obterMetodoPagamentoPorId(id);
        return metodoPagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para atualizar um método de pagamento existente.
     *
     * @param id O ID do método de pagamento a ser atualizado.
     * @param metodoPagamento O objeto MetodoPagamentoModel com os novos dados.
     * @return Resposta com o método de pagamento atualizado ou status 404 (Not Found) se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamentoModel> atualizarMetodoPagamento(
            @PathVariable Integer id,
            @RequestBody MetodoPagamentoModel metodoPagamento) {
        Optional<MetodoPagamentoModel> metodoAtualizado = metodoPagamentoService.atualizarMetodoPagamento(id, metodoPagamento);
        return metodoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para excluir um método de pagamento pelo ID.
     *
     * @param id O ID do método de pagamento a ser excluído.
     * @return Resposta com status 204 (No Content) se a exclusão for bem-sucedida,
     *         ou status 404 (Not Found) se o método de pagamento não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMetodoPagamento(@PathVariable Integer id) {
        try {
            metodoPagamentoService.deletarMetodoPagamento(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para buscar métodos de pagamento pelo nome.
     *
     * @param nome O nome do método de pagamento a ser buscado.
     * @return Uma lista de métodos de pagamento que correspondem ao nome fornecido e status 200 (OK).
     */
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<MetodoPagamentoModel>> buscarPorNome(@PathVariable String nome) {
        List<MetodoPagamentoModel> metodos = metodoPagamentoService.buscarPorNome(nome);
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    /**
     * Endpoint para listar métodos de pagamento com os campos 'nome' e 'taxa', ordenados em ordem crescente.
     *
     * @return Uma lista de objetos DTO contendo o nome e a taxa dos métodos de pagamento e status 200 (OK).
     */
    @GetMapping("/listar/nometaxa")
    public ResponseEntity<List<MetodoPagamentoNomeTaxaDTO>> listarNomeETaxaOrdenado() {
        List<MetodoPagamentoNomeTaxaDTO> listaDTO = metodoPagamentoService.listarNomeETaxaOrdenado();
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }
     /**
     * Endpoint para obter o nome do método de pagamento pelo ID.
     *
     * @param id O ID do método de pagamento a ser buscado.
     * @return Resposta com o nome do método de pagamento encontrado ou status 404 (Not Found) se não encontrado.
     */
    @GetMapping("/nome/{id}")
    public ResponseEntity<String> obterNomeMetodoPagamentoPorId(@PathVariable Integer id) {
        String nome = metodoPagamentoService.getNomeMetodoPagamentoById(id);
        if ("Método de pagamento não encontrado".equals(nome)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(nome, HttpStatus.OK);
}
}
