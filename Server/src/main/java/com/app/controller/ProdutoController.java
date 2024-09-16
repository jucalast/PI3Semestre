package com.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Produto;
import com.app.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
@Validated
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Endpoint para criar um novo produto e associar cafés especiais a ele.
     *
     * @param produto         O produto a ser criado.
     * @param cafeEspecialIds IDs dos cafés especiais a serem associados ao produto.
     * @return Resposta com o produto criado.
     */
    @PostMapping
    public ResponseEntity<Produto> createProduto(
            @RequestBody Produto produto,
            @RequestParam Set<Long> cafeEspecialIds) {
        Produto createdProduto = produtoService.createProduto(produto, cafeEspecialIds);
        return ResponseEntity.ok(createdProduto);
    }
}
