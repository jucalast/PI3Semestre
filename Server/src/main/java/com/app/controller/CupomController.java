package com.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.app.model.CupomModel;
import com.app.service.CupomService;

/**
 * Controlador responsável por expor endpoints para operações relacionadas a cupons.
 * Inclui endpoints para criar, consultar, atualizar e deletar cupons.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/api/cupom")
public class CupomController {

    private final CupomService cupomService;

    @Autowired
    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    /**
     * Endpoint para listar todos os cupons.
     * 
     * @return Lista de todos os cupons.
     */
    @GetMapping
    public List<CupomModel> listarTodos() {
        return cupomService.listarTodosCupons();
    }

    /**
     * Endpoint para buscar um cupom pelo ID.
     * 
     * @param id O ID do cupom.
     * @return O cupom correspondente ao ID, ou 404 se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CupomModel> buscarPorId(@PathVariable Integer id) {
        Optional<CupomModel> cupom = cupomService.buscarCupomPorId(id);
        return cupom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar um novo cupom.
     * 
     * @param cupom O cupom a ser criado.
     * @return O cupom criado.
     */
    @PostMapping
    public CupomModel criarCupom(@RequestBody CupomModel cupom) {
        return cupomService.salvarCupom(cupom);
    }

    /**
     * Endpoint para atualizar um cupom existente.
     * 
     * @param id O ID do cupom.
     * @param cupom Os novos dados do cupom.
     * @return O cupom atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CupomModel> atualizarCupom(@PathVariable Integer id, @RequestBody CupomModel cupom) {
        Optional<CupomModel> cupomAtualizado = cupomService.atualizarCupom(id, cupom);
        return cupomAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para deletar um cupom.
     * 
     * @param id O ID do cupom a ser deletado.
     * @return Resposta 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCupom(@PathVariable Integer id) {
        cupomService.deletarCupom(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para buscar cupons com base em um intervalo de datas de validade.
     * 
     * A URL para esta rota será algo como `/cupons/por-data`.
     * 
     * @param dataInicio A data de início do intervalo de busca (no formato `yyyy-MM-dd'T'HH:mm:ss`).
     * @param dataFim A data de fim do intervalo de busca (no formato `yyyy-MM-dd'T'HH:mm:ss`).
     * @return Uma lista de cupons que possuem data de validade dentro do intervalo especificado.
     */
    @GetMapping("/por-data")
    public ResponseEntity<List<CupomModel>> buscarCuponsPorData(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        List<CupomModel> cupons = cupomService.buscarCuponsPorData(dataInicio, dataFim);

        if (cupons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cupons, HttpStatus.OK);
    }
}
