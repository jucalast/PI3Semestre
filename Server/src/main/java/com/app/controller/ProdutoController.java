package com.app.controller;

import com.app.service.ProdutoService;
import com.app.model.Produto;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.getAllProdutos();
    }

    @GetMapping("/com-especializacoes")
    public List<Produto> getAllProdutosComEspecializacoes() {
        List<Produto> produtos = produtoService.getAllProdutos();
        // Aqui podemos garantir que as especializações estão carregadas
        for (Produto produto : produtos) {
            Hibernate.initialize(produto.getCafeEspecial());
            Hibernate.initialize(produto.getMetodoPreparo());
        }
        return produtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = Optional.ofNullable(produtoService.getProdutoById(id));
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        // Aqui você deve verificar se é um CafeEspecial ou MetodoPreparo
        if (produto.getCafeEspecial() != null) {
            produto.getCafeEspecial().setProduto(produto);
        } else if (produto.getMetodoPreparo() != null) {
            produto.getMetodoPreparo().setProduto(produto);
        }

        Produto savedProduto = produtoService.createProduto(produto);
        return ResponseEntity.ok(savedProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Optional<Produto> existingProduto = Optional.ofNullable(produtoService.getProdutoById(id));
        if (existingProduto.isPresent()) {
            produto.setId(id);
            return ResponseEntity.ok(produtoService.updateProduto(id, produto));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Produto>> searchProdutos(@RequestParam String nome) {
        List<Produto> produtos = produtoService.searchProdutosByNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (produtoService.getProdutoById(id) != null) {
            produtoService.deleteProduto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
