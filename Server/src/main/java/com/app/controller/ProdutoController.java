package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.Produto;
import com.app.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Criar um novo produto
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto createdProduto = produtoService.createProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduto);
    }

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    // Obter um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoService.getProdutoById(id);
        return ResponseEntity.ok(produto);
    }

    // Atualizar um produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto updatedProduto = produtoService.updateProduto(id, produto);
        return ResponseEntity.ok(updatedProduto);
    }

    // Deletar um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
