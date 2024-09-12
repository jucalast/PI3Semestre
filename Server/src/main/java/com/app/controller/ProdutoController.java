package com.app.controller;

import com.app.model.Produto;
import com.app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/produtos")
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
