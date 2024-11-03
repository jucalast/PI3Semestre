package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.MetodoPreparo;
import com.app.service.MetodoPreparoService;

/**
 * Controlador responsável pelas operações relacionadas ao Método de Preparo.
 *
 * Este controlador fornece endpoints para criar e buscar métodos de preparo
 * associados a um produto específico.
 *
 * @author JoãO
 * @version 1.0
 * @since 2024-10-05
 */
@RestController
@RequestMapping("/api/metodo-preparo")
public class MetodoPreparoController {

    @Autowired
    private MetodoPreparoService metodoPreparoService;

    /**
     * Endpoint para criar um novo Método de Preparo.
     *
     * @param metodoPreparo Objeto MetodoPreparo a ser criado.
     * @return O método de preparo recém-criado com o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<MetodoPreparo> createMetodoPreparo(@RequestBody MetodoPreparo metodoPreparo) {
        MetodoPreparo createdMetodo = metodoPreparoService.createMetodoPreparo(metodoPreparo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMetodo);
    }

    /**
     * Endpoint para buscar um Método de Preparo associado a um produto
     * específico.
     *
     * @param id O ID do produto.
     * @return O método de preparo associado ao produto, ou uma resposta 404
     * caso não seja encontrado.
     */
    @GetMapping("/produto/{id}")
    public ResponseEntity<MetodoPreparo> buscarMetodoPreparoPorProdutoId(@PathVariable Long id) {
        MetodoPreparo metodoPreparo = metodoPreparoService.buscarMetodoPreparoPorProdutoId(id);
        return metodoPreparo != null ? ResponseEntity.ok(metodoPreparo) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
