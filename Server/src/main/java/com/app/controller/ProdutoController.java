package com.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Produto;
import com.app.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Rota para listar os valores dos campos solicitados
    @GetMapping("/atributos")
    public ResponseEntity<List<Map<String, Object>>> listarAtributos() {
        List<Map<String, Object>> atributos = produtoService.listarAtributos();
        return ResponseEntity.ok(atributos);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (produtoService.getProdutoById(id) != null) {
            produtoService.deleteProduto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar-por-atributo/{atributo}/{valor}")
    public ResponseEntity<List<Produto>> buscarProdutosPorAtributo(
            @PathVariable String atributo, @PathVariable String valor) {
        List<Produto> produtos = produtoService.buscarProdutosPorAtributo(atributo, valor);
        return ResponseEntity.ok(produtos);
    }

}
